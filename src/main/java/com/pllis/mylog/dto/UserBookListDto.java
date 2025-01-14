package com.pllis.mylog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserBookListDto {
    // USER 테이블 관련 필드
    private Integer userNo;
    private String userMail;
    private String userNickname;

    // MY_BOOK 테이블 관련 필드
    private Integer myBookNo;
    private String bookTitle;
    private String bookImageLink;
    private String readStatus;
    private Integer totalPage;
    private Integer readPage;
    private Integer scope;
    private LocalDateTime registrationDatetime;
    private LocalDateTime updateDatetime;
}
