package com.pllis.mylog.controller;

import com.pllis.mylog.common.handler.ApiResponseHandler;
import com.pllis.mylog.dto.AuthDto;
import com.pllis.mylog.dto.UserBookListDto;
import com.pllis.mylog.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name="UserBookList API",description = "등록된 책 불러오기 API")

@ResponseBody
public class BookController extends BaseController{
    private final BookService bookService;
    private  static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @Operation(summary = "등록된 책 리스트", description = "등록된 책 리스트 API 입니다.")
    @GetMapping("/book")
    @ResponseBody
    public ApiResponseHandler<List<UserBookListDto>> getUserBooks() throws Exception {
        logger.info("메인페이지");
        List<UserBookListDto> userBooks = bookService.getUserBooks();  // 서비스에서 책 리스트 가져오기
        // ApiResponseHandler로 감싸서 반환
        return new ApiResponseHandler<>(userBooks, 200, "사용자의 책 리스트를 성공적으로 가져왔습니다.");
    }

}

