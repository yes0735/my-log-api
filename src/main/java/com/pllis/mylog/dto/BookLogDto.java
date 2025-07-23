package com.pllis.mylog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 책 기록 기본 DTO
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookLogDto {
    private Integer myBookNo;
    private String contents;
}
