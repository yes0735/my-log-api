package com.pllis.mylog.controller;

import com.pllis.mylog.common.utils.NoAuth;
import com.pllis.mylog.service.BookApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pllis.mylog.common.handler.ApiResponseHandler;
import com.pllis.mylog.dto.BookDto;
import com.pllis.mylog.dto.CommonDto;

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
public class BookApiController extends BaseController {

    private final BookApiService bookApiService;

    /**
     * 알라딘 책 검색
     * @return
     */
    @NoAuth
    @Operation(summary = "알라딘 책 검색 리스트 조회", description = "알라딘 책 검색 리스트 조회 API")
    @GetMapping("/search-book")
    @ResponseBody
    public ApiResponseHandler<CommonDto.ListResponse<BookDto.BookSearchResponse>> getSearchBookList(@RequestParam String searchType,
                                                                                                    @RequestParam String searchValue) {

        CommonDto.ListResponse<BookDto.BookSearchResponse> result = bookApiService.getSearchBookList(searchType, searchValue);

        return new ApiResponseHandler<>(result, 200, "ok");
    }

}
