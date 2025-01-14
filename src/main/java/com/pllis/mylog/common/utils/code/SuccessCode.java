package com.pllis.mylog.common.utils.code;

import lombok.Getter;

@Getter
public enum SuccessCode {

	/**
	 * ******************************* Success CodeList ***************************************
	 */
	// 조회 성공 코드 (HTTP Response: 200 OK)
	SELECT_SUCCESS(200, "SELECT SUCCESS"),
	// 삭제 성공 코드 (HTTP Response: 200 OK)
	DELETE_SUCCESS(200, "DELETE SUCCESS"),
	// 삽입 성공 코드 (HTTP Response: 201 Created)
	INSERT_SUCCESS(201, "INSERT SUCCESS"),
	// 수정 성공 코드 (HTTP Response: 201 Created)
	UPDATE_SUCCESS(204, "UPDATE SUCCESS"),

	; // End

	/**
	 * ******************************* Success Code Constructor ***************************************
	 */
	// 성공 코드의 '코드 상태'를 반환한다.
	private final int status;

	// 성공 코드의 '코드 메시지'를 반환한다.s
	private final String message;

	// 생성자 구성
	private SuccessCode(final int status, final String message) {
		this.status = status;
		this.message = message;
	}
}
