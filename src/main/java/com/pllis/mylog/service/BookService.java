package com.pllis.mylog.service;

import com.pllis.mylog.dto.UserBookListDto;
import com.pllis.mylog.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    public final BookRepository bookRepository;

    /**
     * 사용자가 등록한 책 불러오기
     * @param userNo 사용자 번호
     * @return 책 리스트
    **/
    public Page<UserBookListDto> getUserBooks(Integer userNo, Pageable pageable,String readStatus) {

        if(readStatus.equals("null")){
            return bookRepository.findUserBookList(userNo,pageable,readStatus);
        }else{
            return bookRepository.findUserBookListReadStatus(userNo,pageable,readStatus);
        }

    }
}
