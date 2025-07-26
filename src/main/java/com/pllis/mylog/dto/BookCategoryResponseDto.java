package com.pllis.mylog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookCategoryResponseDto {
    private Integer bookCategoryNo;
    private String bookCategoryName;
}
