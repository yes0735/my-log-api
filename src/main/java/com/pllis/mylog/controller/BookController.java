package com.pllis.mylog.controller;

import com.pllis.mylog.common.handler.ApiResponseHandler;
import com.pllis.mylog.dto.UserBookListDto;
import com.pllis.mylog.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 나의 책
 * @author 이예지
 * @version 1.0
 **/
@Slf4j
@RestController
@Tag(name="UserBook API",description = "나의 책 API")
@RequiredArgsConstructor
public class BookController extends BaseController{
    private final BookService bookService;
    
    /**
     * 등록된 책 리스트
     * @return 나의 책 리스트
     **/
    
    @Operation(summary = "등록된 책 리스트", description = "등록된 책 리스트 API 입니다.")
    @GetMapping("/book")
    public ApiResponseHandler<Page<UserBookListDto>> getUserBooks(HttpServletRequest request,
                                                                  @PageableDefault(size=12,sort = "registrationDatetime")Pageable pageable,
                                                                  @RequestParam(required = false) String readStatus ,
                                                                  @RequestParam(required = false) String sortBy,
                                                                  @RequestParam(required = false) String collectionType) {
        log.info("user 메인 페이지 입니다.");
        log.info("탭:{}",readStatus);

        //토큰에서 userNo추출
        Integer userNo = (Integer) request.getAttribute("userNo");
        if(userNo==null){
            throw new IllegalStateException("userNo가 요청에서 누락되었습니다.");
        }
        
        //파라미터 sortBy 기준으로 pageable에 정렬 적용
        Pageable sortedPageable = applySort(pageable, sortBy);
        
        //책 리스트 호출, userNo, 페이징 정보 전달
        Page<UserBookListDto> userBooks = bookService.getUserBooks(userNo,sortedPageable,readStatus,collectionType);
        log.info(userBooks.toString());
        return new ApiResponseHandler<>(userBooks, 200, "사용자의 책 리스트를 성공적으로 가져왔습니다.");
    }
    /**
     * Pageable에 파라미터로 요청한 정렬 조건 반영
     * @param pageable 기존 페이지 정보(Page 번호, 사이즈, 정렬 등)
     * @param sortBy 정렬 기준 문자열 ("latestOrder" 또는 "scope")
     * @return 정렬 조건이 적용된 Pageable 객체
     * */
    private Pageable applySort(Pageable pageable, String sortBy) {
        if (sortBy == null || sortBy.equals("null")) return pageable;

        Sort sort;
        switch (sortBy) {
            case "latestOrder":
                sort = Sort.by(Sort.Direction.DESC, "registrationDatetime");
                break;
            case "scope":
                sort = Sort.by(Sort.Direction.DESC, "scope");
                break;
            default:
                sort = pageable.getSort();
        }
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

    }

}

