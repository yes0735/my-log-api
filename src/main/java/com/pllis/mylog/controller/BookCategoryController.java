package com.pllis.mylog.controller;

import com.pllis.mylog.common.handler.ApiResponseHandler;
import com.pllis.mylog.dto.BookCategoryResponseDto;
import com.pllis.mylog.service.BookCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name="BookCategory API",description = "책 카테고리 API")
public class BookCategoryController extends BaseController{
    private final BookCategoryService bookCategoryService;

    /**
     * 책 기록 등록 시 선택 가능한(드롭다운) 책 카테고리 목록 조회
     * @return 전체 카테고리 List(DTO 리스트)
     **/

    @Operation(summary = "카테고리 불러오기")
    @GetMapping("/book-category")
    public ApiResponseHandler<List<BookCategoryResponseDto>> getBookCategory(){
        List<BookCategoryResponseDto> categories =bookCategoryService.getAllCategory();
        return new ApiResponseHandler<>(categories ,200,"카테고리를 불러왔습니다.");
    }
}
