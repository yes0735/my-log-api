package com.pllis.mylog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class indexController {
    private static final Logger logger = LoggerFactory.getLogger(indexController.class);
    // GET 요청을 처리하는 메서드
    @RequestMapping("/")
    public String myLog(){
        // 로깅을 통해 로그 출력
        logger.info("인덱스");
        System.out.println("헤이");
        return "index"; // index.html을 반환
    }
}