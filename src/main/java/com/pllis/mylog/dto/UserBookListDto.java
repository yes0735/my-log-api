package com.pllis.mylog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserBookListDto {
    // USER 테이블 필드
    private int userNo;  // userNo 타입을 int로 변경
    private String userMail;
    private String userNickname;

    // MY_BOOK 테이블 필드
    private int myBookNo;
    private String bookTitle;
    private String bookImageLink;
    private String readStatus;
    private Integer totalPage;
    private Integer readPage;
    private Integer scope;
    private Date registrationDatetime;
    private Date updateDatetime;

    // 생성자
    public UserBookListDto(Integer userNo, String userMail, String userNickname,
                           Integer myBookNo, String bookTitle, String bookImageLink,
                           String readStatus, Integer totalPage, Integer readPage,
                           Integer scope, Date registrationDatetime,
                           Date updateDatetime) {
        this.userNo = userNo;
        this.userMail = userMail;
        this.userNickname = userNickname;
        this.myBookNo = myBookNo;
        this.bookTitle = bookTitle;
        this.bookImageLink = bookImageLink;
        this.readStatus = readStatus;
        this.totalPage = totalPage;
        this.readPage = readPage;
        this.scope = scope;
        this.registrationDatetime = registrationDatetime;
        this.updateDatetime = updateDatetime;
    }
}
