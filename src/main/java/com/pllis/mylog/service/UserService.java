package com.pllis.mylog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pllis.mylog.domain.MyBook;
import com.pllis.mylog.dto.BookDto;
import com.pllis.mylog.dto.CommonDto;
import com.pllis.mylog.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final BookRepository bookRepository;

//    /**
//     * 사용자 정보 등록
//     * @return
//     */
//    @Transactional(readOnly = true)
//    public CommonDto.ListResponse<MyBook> getBookList(){
//
//        List<MyBook> list = bookRepository.findAll();
//        Integer count = (int) bookRepository.count();
//
//		return CommonDto.ListResponse.<MyBook>builder()
//				.list(list)
//				.count(count)
//				.build();
//    }

}