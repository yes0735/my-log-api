package com.pllis.mylog.controller;

import com.pllis.mylog.common.handler.ApiResponseHandler;
import com.pllis.mylog.dto.UserBookListDto;
import com.pllis.mylog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name="UserBookList API",description = "등록된 책 불러오기 API")

@ResponseBody
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "등록된 책 리스트", description = "등록된 책 리스트 API 입니다.")
    @GetMapping("/{userNo}")  // URL 경로에서 userNo를 동적으로 받기 위해 PathVariable 사용
    @ResponseBody
    public ApiResponseHandler<List<UserBookListDto>> getUserBooks(@PathVariable int userNo) {
        List<UserBookListDto> userBooks = userService.getUserBooks(userNo);  // 서비스에서 책 리스트 가져오기
        // ApiResponseHandler로 감싸서 반환
        return new ApiResponseHandler<>(userBooks, 200, "사용자의 책 리스트를 성공적으로 가져왔습니다.");
    }

}

