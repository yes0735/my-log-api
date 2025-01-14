package com.pllis.mylog.repository;

import com.pllis.mylog.domain.User;
import com.pllis.mylog.dto.UserBookListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {  // JpaRepository로 변경
    @Query("SELECT new com.pllis.mylog.dto.UserBookListDto(" +
            "U.userNo, " +
            "U.userMail, " +
            "U.userNickname, " +
            "M.myBookNo, " +
            "M.bookTitle, " +
            "M.bookImageLink, " +
            "M.readStatus, " +
            "M.totalPage, " +
            "M.readPage, " +
            "M.scope, " +
            "M.registrationDatetime, " +
            "M.updateDatetime) " +
            "FROM User U " +
            "INNER JOIN MyBook M ON U.userNo = M.userNo " +
            "WHERE U.userNo = :userNo")
    List<UserBookListDto> findUserBookList(@Param("userNo") Integer userNo);

}
