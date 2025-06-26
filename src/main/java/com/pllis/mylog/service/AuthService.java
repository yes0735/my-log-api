package com.pllis.mylog.service;

import com.pllis.mylog.common.config.JwtConfig;
import com.pllis.mylog.common.utils.code.EncryptionUtils;
import com.pllis.mylog.domain.User;
import com.pllis.mylog.dto.AuthDto;
import com.pllis.mylog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtConfig jwtConfig;

    @Transactional(readOnly = true)
    public AuthDto.Login2FAResponse loginProc(AuthDto.LoginRequest loginRequest, HttpServletResponse response) throws Exception {

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

}