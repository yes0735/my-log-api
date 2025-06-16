package com.pllis.mylog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * DTO? 각 계층 간을 이동할 때 데이터를 전달해주는 클래스
 */
@Data
@AllArgsConstructor
public class AuthDto {

	@Builder
	public record LoginRequest(
        @NotBlank(message = "이메일 입력하세요")
        @Schema(title = "사용자 이메일", description = "로그인 이메일")
        String loginId,

        @NotBlank(message = "비밀번호를 입력하세요")
        @Schema(title = "사용자 비밀번호", description = "사용자 비밀번호")
        String loginPw
	) {}

    @Builder
    public record RefreshTokenRequest(
            @NotBlank(message = "갱신 토큰이 비어있습니다.")
            @Schema(title = "리프레쉬 토큰")
            String refreshToken
    ) {}

    @Builder
    public record LoginResponse(
            @Schema(title = "로그인 인증키", description = "로그인 인증키")
            String key
    ) {}

    @Builder
    public record Login2FAResponse(
            @Schema(title = "액세스 토큰", description = "7일 유효")
            String accessToken,
            @Schema(title = "리프레쉬 토큰", description = "14일 유효")
            String refreshToken
    ) {}
}

