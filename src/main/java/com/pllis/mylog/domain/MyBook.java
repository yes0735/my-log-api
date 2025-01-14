package com.pllis.mylog.domain;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 나의책 테이블
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "MY_BOOK")
public class MyBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MY_BOOK_NO")
    private Integer myBookNo;
    @Column(name = "USER_NO")
    private Integer userNo;
    @Column(name = "BOOK_TITLE")
    private String bookTitle;
    @Column(name = "AUTHOR")
    private String author;
    @Column(name = "PUBLISHER")
    private String publisher;
    @Column(name = "CATEGORY")
    private String category;
    @Column(name = "BOOK_IMAGE_LINK")
    private String bookImageLink;
    @Column(name = "COLLECTION_TYPE")
    private String collectionType;
    @Column(name = "READ_STATUS")
    private String readStatus;
    @Column(name = "READ_START_DATE")
    private Date readStartDate;
    @Column(name = "READ_END_DATE")
    private Date readEndDate;
    @Column(name = "TOTAL_PAGE")
    private Integer totalPage;
    @Column(name = "READ_PAGE")
    private Integer readPage;
    @Column(name = "SCOPE")
    private Integer scope;
    @Column(name = "REGISTRATION_DATETIME")
    private Date registrationDatetime;
    @Column(name = "UPDATE_DATETIME")
    private Date updateDatetime;
}
