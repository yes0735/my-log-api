package com.pllis.mylog.common.handler;

import lombok.Builder;
import lombok.Getter;

/**
 * [공통] API Response 결과의 반환 값을 관리
 */
@Getter
public class ApiResponseHandler<T> {

	// API 응답 결과 Response
	private final T result;

	// API 응답 코드 Response
	private final int status;

	// API 응답 코드 Message
	private final String message;

	@Builder
	public ApiResponseHandler(final T result, final int resultCode, final String resultMsg) {
		this.result = result;
		this.status = resultCode;
		this.message = resultMsg;
	}

	@Builder
	public ApiResponseHandler(final int resultCode, final String resultMsg) {
		this.result = null;
		this.status = resultCode;
		this.message = resultMsg;
	}

}
