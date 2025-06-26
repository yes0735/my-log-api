package com.pllis.mylog.service;

import com.pllis.mylog.common.config.JwtConfig;
import com.pllis.mylog.dto.AuthDto;
import com.pllis.mylog.dto.UserBookListDto;
import com.pllis.mylog.repository.BookRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    public final BookRepository bookRepository;
    private final HttpServletRequest context;

    public List<UserBookListDto> getUserBooks() {
        Integer userNo = (Integer) context.getAttribute("userNo");
        return bookRepository.findUserBookList(userNo);
    }
}
