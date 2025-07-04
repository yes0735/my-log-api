package com.pllis.mylog.repository;

import com.pllis.mylog.domain.MyBook;
import com.pllis.mylog.dto.UserBookListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<MyBook, Integer> {  // JpaRepository로 변경
    //기본 조회
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
            "M.updateDatetime )" +
            "FROM MyBook M " +
            "INNER JOIN User U ON U.userNo = M.userNo " +
            "LEFT JOIN CommonCode R ON M.readStatus = R.code AND R.useYn = 'Y' " +
            "LEFT JOIN CommonCode C ON M.collectionType = C.code AND C.useYn = 'Y' " +
            "WHERE U.userNo = :userNo")

    Page<UserBookListDto> findUserBookList(@Param("userNo") Integer userNo,
                                           Pageable pageable,
                                           @Param("readStatus") String readStatus);

   //탭별 조회
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
            "M.updateDatetime )" +
            "FROM MyBook M " +
            "INNER JOIN User U ON U.userNo = M.userNo " +
            "LEFT JOIN CommonCode R ON M.readStatus = R.code AND R.useYn = 'Y' " +
            "LEFT JOIN CommonCode C ON M.collectionType = C.code AND C.useYn = 'Y' " +
            "WHERE U.userNo = :userNo AND M.readStatus = :readStatus")
    Page<UserBookListDto> findUserBookListReadStatus(@Param("userNo") Integer userNo,Pageable pageable, @Param("readStatus") String readStatus);


    //소장 유형별 조회
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
            "M.updateDatetime )" +
            "FROM MyBook M " +
            "INNER JOIN User U ON U.userNo = M.userNo " +
            "LEFT JOIN CommonCode R ON M.readStatus = R.code AND R.useYn = 'Y' " +
            "LEFT JOIN CommonCode C ON M.collectionType = C.code AND C.useYn = 'Y' " +
            "WHERE U.userNo = :userNo AND M.collectionType = :collectionType")
    Page<UserBookListDto> findUserBookListCollectionType(@Param("userNo") Integer userNo, Pageable pageable, @Param("collectionType") String collectionType);

    //탭&소장유형별
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
            "M.updateDatetime )" +
            "FROM MyBook M " +
            "INNER JOIN User U ON U.userNo = M.userNo " +
            "LEFT JOIN CommonCode R ON M.readStatus = R.code AND R.useYn = 'Y' " +
            "LEFT JOIN CommonCode C ON M.collectionType = C.code AND C.useYn = 'Y' " +
            "WHERE U.userNo = :userNo AND M.readStatus = :readStatus AND M.collectionType = :collectionType")
    Page<UserBookListDto> findUserBookListReadStatusAndCollectionType(@Param("userNo") Integer userNo, Pageable pageable,
                                                                      @Param("readStatus") String readStatus,@Param("collectionType") String collectionType);
}
