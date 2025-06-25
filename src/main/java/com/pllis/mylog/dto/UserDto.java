package com.pllis.mylog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO? 각 계층 간을 이동할 때 데이터를 전달해주는 클래스
 */
@Data
@AllArgsConstructor
public class UserDto {

	@Builder
	public record JoinRequest(
        @NotBlank(message = "이메일 입력하세요")
        @Schema(title = "사용자 이메일", description = "로그인 이메일")
        String userMail,

        @NotBlank(message = "비밀번호를 입력하세요")
        @Schema(title = "사용자 비밀번호", description = "사용자 비밀번호")
        String userPassword,

        @NotBlank(message = "닉네임을 입력하세요")
        @Schema(title = "사용자 닉네임", description = "사용자 닉네임")
        String userNickname
	) {}
}

