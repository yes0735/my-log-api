package com.pllis.mylog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 나의 책 기본 DTO
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyBookDto {
    private String  bookTitle;
    private String author;
    private String publisher;
    private String category;
    private String bookImageLink;
    private String collectionType;
    private String readStatus;
    private LocalDateTime readStartDate;
    private LocalDateTime readEndDate;
    private Integer totalPage;
    private Integer readPage;
    private Integer scope;
}
