package com.pllis.mylog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Date;

@Getter
@AllArgsConstructor

/**
 *  사용자가 등록한 책 리스트 조회 응답DTO
 *
 * */
public class MyBookListResponseDto {

    //MYBOOK 테이블 관련 필드
    private Integer myBookNo;
    private String bookTitle;
    private String bookImageLink;
    private String readStatus;    // COMMON_CODE 테이블 관련 필드
    private String collectionType;    // COMMON_CODE 테이블 관련 필드
    private Integer totalPage;
    private Integer readPage;
    private Integer scope;
    private Date registrationDatetime;
    private Date updateDatetime;
}
