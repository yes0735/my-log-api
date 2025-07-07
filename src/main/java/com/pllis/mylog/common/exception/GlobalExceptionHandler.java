package com.pllis.mylog.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<?> handleUnauthenticated(UnauthenticatedException ex) {
        System.out.println("여기까지 왔음 - handleUnauthenticated 호출됨");
        log.warn("🔐 인증되지 않은 사용자 요청: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", ex.getMessage()));
    }
}
