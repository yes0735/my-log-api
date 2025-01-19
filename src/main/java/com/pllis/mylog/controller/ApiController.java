package com.pllis.mylog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pllis.mylog.common.handler.ApiResponseHandler;
import com.pllis.mylog.dto.BookDto;
import com.pllis.mylog.dto.CommonDto;
import com.pllis.mylog.service.ApiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * 독서 클래스
 *
 * @author 이경희
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Book API", description = "Swagger Book 테스트용 API")
public class ApiController extends BaseController {

    private final ApiService apiService;

    /**
     * 책장 리스트 조회
     * @return BookDto
     */
/*    @Operation(summary = "리스트", description = "리스트 API 입니다.")
    // @Parameter(name = "str", description = "2번 반복할 문자열")
    @GetMapping("/book")
    @ResponseBody
    public ApiResponseHandler<CommonDto.ListResponse<MyBook>> getBookList() {
        CommonDto.ListResponse<MyBook> result = bookService.getBookList();

        return new ApiResponseHandler<>(result, 200, "ok");
    }*/

    /**
     * 책장 리스트 조회
     * @return BookDto
     */
    @Operation(summary = "알라딘 책 검색 리스트 조회", description = "알라딘 책 검색 리스트 조회 API")
    @GetMapping("/search-book")
    @ResponseBody
    public ApiResponseHandler<CommonDto.ListResponse<BookDto.BookSearchResponse>> getSearchBookList() {
        CommonDto.ListResponse<BookDto.BookSearchResponse> result = apiService.getSearchBookList();

        return new ApiResponseHandler<>(result, 200, "ok");
    }
    // @Operation(summary = "네이버 책 검색 리스트 조회", description = "리스트 API 입니다.")
    // @GetMapping("/search-book")
    // @ResponseBody
    // public List<BookVo> getSearchBookList() {
    //     return bookService.getSearchBookList();
    // }

    /**
     * 나의 책 조회
     * */


}
