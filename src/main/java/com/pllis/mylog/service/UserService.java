package com.pllis.mylog.service;

import com.pllis.mylog.dto.UserBookListDto;
import com.pllis.mylog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserBookListDto> getUserBooks(int userNo){
        return userRepository.findUserBookList(userNo);
    }
}
