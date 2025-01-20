package com.pllis.mylog.controller;

import com.pllis.mylog.common.handler.ApiResponseHandler;
import com.pllis.mylog.domain.MyBook;
import com.pllis.mylog.dto.BookDto;
import com.pllis.mylog.dto.CommonDto;
import com.pllis.mylog.service.BookService;
import com.pllis.mylog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자
 *
 * @author 이경희
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User API", description = "Swagger User 테스트용 API")
public class UserController extends BaseController {

    private final UserService userService;

//    /**
//     * 회원가입
//     * @return BookDto
//     */
//    @Operation(summary = "회원가입", description = "회원가입 API")
//    // @Parameter(name = "str", description = "2번 반복할 문자열")
//    @PostMapping
//    @ResponseBody
//    public ApiResponseHandler<CommonDto.ListResponse<MyBook>> postUser() {
//        CommonDto.ListResponse<MyBook> result = userService.getBookList();
//
//        return new ApiResponseHandler<>(result, 200, "ok");
//    }

}
