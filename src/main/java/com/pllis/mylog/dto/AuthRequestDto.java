package com.pllis.mylog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO? 각 계층 간을 이동할 때 데이터를 전달해주는 클래스
 */
@Data
@AllArgsConstructor
public class AuthRequestDto {

	@Builder
	public record VerifyRequestDto(
        String phoneNumber,
        String code
	) {}
}

