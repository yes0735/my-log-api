package com.pllis.mylog.service;

import com.pllis.mylog.dto.UserBookListDto;
import com.pllis.mylog.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    public final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<UserBookListDto> getUserBooks(int userNo){
        return bookRepository.findUserBookList(userNo);
    }
}
