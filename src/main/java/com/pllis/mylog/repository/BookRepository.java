package com.pllis.mylog.repository;

import com.pllis.mylog.domain.User;
import com.pllis.mylog.dto.UserBookListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<User, Integer> {  // JpaRepository로 변경
    @Query("SELECT new com.pllis.mylog.dto.UserBookListDto(" +
            "U.userNo, " +
            "U.userMail, " +
            "U.userNickname, " +
            "M.myBookNo, " +
            "M.bookTitle, " +
            "M.bookImageLink, " +
            "M.readStatus, " +
            "R.codeName AS readStatusName, " +
            "M.collectionType, " +
            "C.codeName AS collectionTypeName, " +
            "M.totalPage, " +
            "M.readPage, " +
            "M.scope, " +
            "M.registrationDatetime, " +
            "M.updateDatetime) " +
            "FROM User U " +
            "INNER JOIN MyBook M ON U.userNo = M.userNo " +
            "LEFT JOIN CommonCode R ON M.readStatus = R.code AND R.useYn = 'Y' " +
            "LEFT JOIN CommonCode C ON M.collectionType = C.code AND C.useYn = 'Y' " +
            "WHERE U.userNo = :userNo")
    List<UserBookListDto> findUserBookList(@Param("userNo") Integer userNo);

}
