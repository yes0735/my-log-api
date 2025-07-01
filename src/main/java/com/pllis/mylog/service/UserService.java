package com.pllis.mylog.service;

import com.pllis.mylog.common.exception.DuplicateUserException;
import com.pllis.mylog.common.utils.code.EncryptionUtils;
import com.pllis.mylog.domain.User;
import com.pllis.mylog.dto.CommonDto;
import com.pllis.mylog.dto.UserDto;
import com.pllis.mylog.repository.BookRepository;
import com.pllis.mylog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    /**
     * 사용자 정보 등록
     * @return
     */
    @Transactional
    public CommonDto.trueResponse postUserJoin(UserDto.JoinRequest requestDto) throws Exception {

        // TODO: 이메일 중복체크 로직
//        Optional<User> optionalUser = userRepository.findUserByUserMail(requestDto.userMail());
//        optionalUser.orElseThrow(() -> new EntityNotFoundException("이미 사용중인 이메일 입니다."));

        try {
            userRepository.findUserByUserMail(requestDto.userMail())
                    .ifPresent(user -> {
                        throw new DuplicateUserException("이미 등록된 이메일입니다");
                    });

            User user = User.builder()
                    .userMail(requestDto.userMail())
                    .userPassword(EncryptionUtils.encryptSHA512(requestDto.userPassword()))
                    .userNickname(requestDto.userNickname())
                    .userMobilePhoneNumber(requestDto.userMobilePhoneNumber())
                    .userStatus("available")
                    .build();

            userRepository.save(user);

            return CommonDto.trueResponse.builder()
                    .isTrue(true)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
//            e.printStackTrace(); // 또는 아무것도 안 함
        }
    }

}