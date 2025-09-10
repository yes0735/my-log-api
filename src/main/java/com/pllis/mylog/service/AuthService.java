package com.pllis.mylog.service;

import com.pllis.mylog.common.config.JwtConfig;
import com.pllis.mylog.common.utils.code.EncryptionUtils;
import com.pllis.mylog.domain.User;
import com.pllis.mylog.dto.AuthDto;
import com.pllis.mylog.dto.AuthRequestDto;
import com.pllis.mylog.repository.AuthRepository;
import com.pllis.mylog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtConfig jwtConfig;
    private final RedisTemplate<String, String> redisTemplate;
    private final NaverSmsService naverSmsService;
//    private final JavaMailSender mailSender;

    private static final String AUTH_CODE_PREFIX = "authCode:";
    private static final String FAIL_COUNT_PREFIX = "authCode:fail:";
    private static final Duration TTL = Duration.ofMinutes(3);
    private static final int MAX_FAIL_COUNT = 5;

    @Transactional(readOnly = true)
    public AuthDto.Login2FAResponse loginProc(AuthDto.LoginRequest loginRequest,
                                              HttpServletResponse response) throws Exception {

        String currentTimeMs = Long.toString(System.currentTimeMillis());
//        final String userType = "user";
        String loginId = loginRequest.loginId();
        String loginPw = loginRequest.loginPw();

//        System.out.println(EncryptionUtils.encryptSHA512(loginPw));

        Optional<User> optionalUser = userRepository.findUserByUserMail(loginId);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));
        if (!user.getUserPassword().equals(EncryptionUtils.encryptSHA512(loginPw))) throw new EntityNotFoundException("아이디 또는 패스워드가 일치 하지 않습니다.");

        String accessToken = jwtConfig.createAccessToken(loginId, optionalUser.get().getUserNo());
        String refreshToken = jwtConfig.createRefreshToken(loginId, optionalUser.get().getUserNo());

        return AuthDto.Login2FAResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    public AuthDto.Login2FAResponse refreshProc(AuthDto.RefreshTokenRequest body) throws Exception {
        String getRefreshToken = body.refreshToken();

        // 유효한 토큰인지 체크
        jwtConfig.validateToken(getRefreshToken);

        String loginId = jwtConfig.getUserMail(getRefreshToken);

        Optional<User> optionalUser = userRepository.findUserByUserMail(loginId);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));

        String accessToken = jwtConfig.createAccessToken(loginId, optionalUser.get().getUserNo());
        String refreshToken = jwtConfig.createRefreshToken(loginId, optionalUser.get().getUserNo());

        return AuthDto.Login2FAResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    /**
     * 인증번호 요청
     * @return
     */
    public void sendFindAccountCode(String phoneNumber) throws Exception {
        String redisKey = AUTH_CODE_PREFIX + phoneNumber;

        // 이미 인증번호가 있는 경우 - 중복 발송 방지
        if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
            throw new IllegalStateException("이미 인증번호가 전송되었습니다. 3분 후 다시 시도해주세요.");
        }

        // 인증번호 생성 (6자리)
        String authCode = generateCode();

        // Redis 저장 (3분 유효)
        redisTemplate.opsForValue().set(redisKey, authCode, TTL);
        redisTemplate.delete(FAIL_COUNT_PREFIX + phoneNumber); // 전송 시 실패 카운트 초기화

        // 문자 발송
        // TODO: 네이버 SMS 사용시 네이버 SENS 설정 필요 (네이버 클라우드 플랫폼에서 SMS 서비스 신청 후 발급)
        String content = "[MyLog] 인증번호는 [" + authCode + "] 입니다.";
        System.out.println("################### " + content);
        // TODO: 네이버 SMS 서비스 사용 신청완료 후 주석 제거해서 테스트 해보기
//        naverSmsService.sendSms(phoneNumber, content);

        // 이메일 발송
        // 이메일 사용시 네이버 SMTP 설정 필요 (낮은 보안의 테스트 메일 계정 사용 권장)
        // sendEmail(to, authCode);
    }

    /**
     * 인증번호 검증
     */
    public String verifyFindAccountCode(AuthRequestDto.VerifyRequestDto verifyRequestDto) throws Exception {

        String codeKey = AUTH_CODE_PREFIX + "{\"phoneNumber\":\"" + verifyRequestDto.phoneNumber() + "\"}"; // Redis 키 생성
        String failKey = FAIL_COUNT_PREFIX + "{\"phoneNumber\":\"" + verifyRequestDto.phoneNumber() + "\"}"; // Redis 키 생성

        String savedCode = redisTemplate.opsForValue().get(codeKey);

        if (savedCode == null) {
            throw new IllegalArgumentException("인증번호가 만료되었거나 존재하지 않습니다.");
        }

        if (savedCode.equals(verifyRequestDto.code())) {
            // 인증 성공
            redisTemplate.delete(codeKey);
            redisTemplate.delete(failKey);
        } else {
            // 실패 횟수 증가
            Long failCount = redisTemplate.opsForValue().increment(failKey);
            redisTemplate.expire(failKey, TTL); // TTL도 동일하게 유지

            if (failCount != null && failCount >= MAX_FAIL_COUNT) {
                throw new IllegalStateException("5회 이상 인증에 실패하여 제한되었습니다.");
            } else {
                throw new IllegalArgumentException("인증번호가 올바르지 않습니다. (" + failCount + "/5)");
            }
        }

        Optional<User> userInfo = userRepository.findUserMailByUserMobilePhoneNumber(verifyRequestDto.phoneNumber());
        return userInfo.get().getUserMail();
    }

    private String generateCode() {
        return String.format("%06d", new Random().nextInt(999999));
//        return String.format("%06d", (int)(Math.random() * 1_000_000));
    }

    /**
     * 이메일로 인증번호 전송 (예시)
     */
//    private void sendEmail(String to, String code) {
//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setTo(to);
//        mail.setSubject("[MyLog] 이메일 인증번호");
//        mail.setText("인증번호: " + code + "\n3분 내에 입력해주세요.");
//        mailSender.send(mail);
//    }

}