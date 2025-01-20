package com.pllis.mylog.controller;

import com.pllis.mylog.common.handler.ApiResponseHandler;
import com.pllis.mylog.common.utils.NoAuth;
import com.pllis.mylog.domain.MyBook;
import com.pllis.mylog.dto.AuthDto;
import com.pllis.mylog.dto.CommonDto;
import com.pllis.mylog.service.AuthService;
import com.pllis.mylog.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirements
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth API", description = "Swagger Auth 테스트용 API")
public class AuthController {

    private final AuthService authService;

    @NoAuth
    @Operation(summary = "로그인", description = "로그인 API ")
    @PostMapping
    @ResponseBody
    public ApiResponseHandler<AuthDto.Login2FAResponse> postLogin(@RequestBody @Valid AuthDto.LoginRequest loginRequest) throws Exception {
        AuthDto.Login2FAResponse result = authService.loginProc(loginRequest);

        return new ApiResponseHandler<>(result, 200, "ok");
    }

    @NoAuth
    @Operation(summary = "토큰 갱신", description = "토큰 갱신 API ")
    @PutMapping
    @ResponseBody
    public ApiResponseHandler<AuthDto.Login2FAResponse> putRefresh(@RequestBody @Valid AuthDto.RefreshTokenRequest refreshTokenRequest) throws Exception {
        AuthDto.Login2FAResponse result = authService.refreshProc(refreshTokenRequest);

        return new ApiResponseHandler<>(result, 200, "ok");
    }
}
