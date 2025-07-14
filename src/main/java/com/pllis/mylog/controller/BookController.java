package com.pllis.mylog.controller;

import com.pllis.mylog.common.handler.ApiResponseHandler;
import com.pllis.mylog.dto.MyBookListResponseDto;
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
import org.springframework.web.bind.annotation.*;

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
     * 등록된 책 리스트를 페이징해 조회
     * 기본 페이지 사이즈는 12이며, 등록일(registrationDatetime)기준으로 정렬
     *
     * @param pageable  pageable 페이징 정보
     * @param readStatus 독서 상태 필터(예:reading,finished 등)
     * @param sortBy 정렬 기준(예:registrationDatetime,scope)
     * @param collectionType 소장 유형 필터(예:ebook,paper_book등)
     * @return 나의 책 리스트
     **/

    @Operation(summary = "등록된 책 리스트", description = "등록된 책 리스트 API 입니다.")
    @GetMapping("/my-book")
    public ApiResponseHandler<Page<MyBookListResponseDto>> getUserBooks(HttpServletRequest request,
                                                                        @PageableDefault(size=12,sort = "registrationDatetime")Pageable pageable,
                                                                        @RequestParam(required = false) String readStatus ,
                                                                        @RequestParam(required = false) String sortBy,
                                                                        @RequestParam(required = false) String collectionType) {
        /*log.info("user 메인 페이지 입니다.");*/
        /*log.info("탭:{}",readStatus);*/

        //토큰에서 userNo추출
        Integer userNo = (Integer) request.getAttribute("userNo");

        //파라미터 sortBy 기준으로 pageable에 정렬 적용
        Pageable sortedPageable = applySort(pageable, sortBy);

        //책 리스트 호출, userNo, 페이징 정보 전달
        Page<MyBookListResponseDto> userBooks = bookService.getMyBookListResponseDtos(userNo,sortedPageable,readStatus,collectionType);
        log.info(userBooks.toString());
        return new ApiResponseHandler<>(userBooks, 200, "사용자의 책 리스트를 성공적으로 가져왔습니다.");
    }

    /**
     * Pageable에 파라미터로 요청한 정렬을 반영하여 새로운 Pageable 객체 반환
     * @param pageable 현재 페이지 정보(Page 번호, 크기, 정렬 등)
     * @param sortBy 정렬 기준 문자열 (예:"registrationDatetime","scope")
     * scope 정렬 시 별점이 높은 최신순으로 정렬
     * @return 정렬 조건이 적용된 Pageable 객체
     * */
    private Pageable applySort(Pageable pageable, String sortBy) {
        if (sortBy == null || sortBy.equals("null")) return pageable;

        Sort sort;
        switch (sortBy) {
            case "registrationDatetime":
                sort = Sort.by(Sort.Direction.DESC, "registrationDatetime");
                break;
            case "scope":
                sort = Sort.by(Sort.Direction.DESC, "scope")
                        .and(Sort.by(Sort.Direction.DESC,"registrationDatetime"));
                break;
            default:
                sort = pageable.getSort();
        }
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

    }

    /**
     * 등록된 책 단건 삭제(soft delete)
     * @param myBookNo 삭제할 나의 책 번호
     * @return 삭제 완료 메시지를 포함한 응답 객체
     **/

    @Operation(summary = "등록된 내 책 삭제", description = "등록된 책 삭제 API 입니다.")
    @DeleteMapping("my-book/{myBookNo}")
    public ApiResponseHandler<Void> deleteUserBook(@PathVariable Integer myBookNo){
        bookService.softDeleteByMyBook(myBookNo);
        return new ApiResponseHandler<>(200,"삭제가 완료되었습니다.");
    }


}

