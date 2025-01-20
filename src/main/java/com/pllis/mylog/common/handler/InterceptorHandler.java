package com.pllis.mylog.common.handler;

import com.pllis.mylog.common.config.JwtConfig;
import com.pllis.mylog.common.utils.NoAuth;
import com.pllis.mylog.domain.User;
import com.pllis.mylog.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterceptorHandler implements HandlerInterceptor {

    @Value("${allow.paths}")
    private final List<String> allowPaths;

    private final JwtConfig jwtConfig;

    private final UserRepository userRepository;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

//    private final ModelMapper modelmapper;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {

        // options는 무조건 통과
        if (HttpMethod.OPTIONS.matches(request.getMethod())) return true;

        if (!(handler instanceof HandlerMethod handlerMethod)) throw new Exception("Not Found");

        Method method = handlerMethod.getMethod();
        String authorizationHeader = request.getHeader("Authorization");

        boolean isExcludedPath = allowPaths.stream().anyMatch(excludePath -> pathMatcher.match(excludePath, request.getRequestURI()));

        /*
         * 예외 URI 또는 메소드에 NoAuth 어노테이션이 있다면 패스
         */
        if (isExcludedPath || method.isAnnotationPresent(NoAuth.class)) return true;

        /*
         * 인증이 필수 인데 헤더가 없을 경우
         */
        if (authorizationHeader == null || (!authorizationHeader.startsWith("Bearer ") && !authorizationHeader.startsWith("bearer "))) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "토큰 정보가 누락되어있습니다.");
        }

        String accessToken = authorizationHeader.substring(7);

        /*
         * 토큰이 없을 경우
         */
        if (!jwtConfig.validateToken(accessToken)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "토큰 정보가 유효하지 않습니다.");
        }

        // [STEP4] 토큰을 기반으로 사용자 아이디를 반환 받는 메서드
        String loginId = jwtConfig.getLoginId(accessToken);
        String userType = jwtConfig.getUserType(accessToken);
        // --
        log.info("loginId: {}", loginId);
        log.info("userType: {}", userType);

        /*
         * 유니크 아이디가 없을경우
         */
        if (loginId == null) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "잘못된 토큰입니다.");
        }

        /*
         * 관리자 DAO 호출
         */

        if (!userType.equals("user")) throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "허용불가");

        Optional<User> user = userRepository.findUserByUserMail(loginId);
        if (user.isEmpty()) throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "존재하지 않는 관리자 입니다");

        request.setAttribute("loginId", loginId);
        request.setAttribute("userType", userType);
        request.setAttribute("loginInfo", user);
//        request.setAttribute("modelmapper", modelmapper);

        return true;
    }

}
