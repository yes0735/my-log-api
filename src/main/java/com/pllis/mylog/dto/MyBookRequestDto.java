package com.pllis.mylog.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyBookRequestDto {
    private List<BookLogDto> bookLogDto;
    private MyBookDto myBookDto;
}
