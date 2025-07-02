package com.pllis.mylog.controller;

import com.pllis.mylog.common.handler.ApiResponseHandler;
import com.pllis.mylog.dto.UserBookListDto;
import com.pllis.mylog.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 나의 책
 * @author 이예지
 * @version 1.0
 **/

@RestController
@Tag(name="UserBook API",description = "나의 책 API")
@RequiredArgsConstructor
public class BookController extends BaseController{
    private final BookService bookService;
    private  static final Logger logger = LoggerFactory.getLogger(BookController.class);
    
    /**
     * 등록된 책 리스트
     * @return 나의 책 리스트
     **/
    
    @Operation(summary = "등록된 책 리스트", description = "등록된 책 리스트 API 입니다.")
    @GetMapping("/book")
    public ApiResponseHandler<Page<UserBookListDto>> getUserBooks(HttpServletRequest request,
                                                                  @PageableDefault(size=12,sort = "registrationDatetime")Pageable pageable) throws Exception {
        logger.info("user 메인 페이지 입니다.");

        //토큰에서 userNo추출
        Integer userNo = (Integer) request.getAttribute("userNo");
        if(userNo==null){
            throw new IllegalStateException("userNo가 요청에서 누락되었습니다.");
        }

        //책 리스트 호출, userNo, 페이징 정보 전달
        Page<UserBookListDto> userBooks = bookService.getUserBooks(userNo,pageable);
        logger.info(userBooks.toString());
        return new ApiResponseHandler<>(userBooks, 200, "사용자의 책 리스트를 성공적으로 가져왔습니다.");
    }

}

