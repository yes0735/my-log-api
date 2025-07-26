package com.pllis.mylog.service;

import com.pllis.mylog.domain.BookCategory;
import com.pllis.mylog.dto.BookCategoryResponseDto;
import com.pllis.mylog.repository.BookCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookCategoryService {
    private final BookCategoryRepository bookCategoryRepository;
    /**
     * 책 카테고리 전체 리스트 조회
     * @return 전체 카테고리 List(DTO 리스트)
     **/
    public List<BookCategoryResponseDto> getAllCategory(){
        List<BookCategory> categories = bookCategoryRepository.findAll();
        return categories.stream().
                map(cat -> new BookCategoryResponseDto(
                        cat.getBookCategoryNo(),
                        cat.getBookCategoryName()))
                .collect(Collectors.toList());
    }

}
