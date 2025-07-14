package com.pllis.mylog.repository;

import com.pllis.mylog.domain.MyBook;
import com.pllis.mylog.dto.MyBookListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
/**
 * CRUD 필요 없는 조회 전용이라 Repository 상속 <엔티티, 기본키자료형>
 **/

public interface MyBookListRepository extends Repository<MyBook,Integer> {
    //기본 조회
    @Query("SELECT new com.pllis.mylog.dto.MyBookListResponseDto(" +
            "M.myBookNo, " +
            "M.bookTitle, " +
            "M.bookImageLink, " +
            "M.readStatus, " +
            "M.collectionType, " +
            "M.totalPage, " +
            "M.readPage, " +
            "M.scope, " +
            "M.registrationDatetime, " +
            "M.updateDatetime )" +
            "FROM MyBook M " +
            "INNER JOIN User U ON U.userNo = M.userNo " +
            "INNER JOIN CommonCode R ON M.readStatus = R.code AND R.useYn = 'Y' " +
            "INNER JOIN CommonCode C ON M.collectionType = C.code AND C.useYn = 'Y' " +
            "WHERE U.userNo = :userNo AND deleteYn='N'")
    Page<MyBookListResponseDto> findUserBookList(@Param("userNo") Integer userNo,
                                                 Pageable pageable);

    //독서 상태(탭)별 조회
    @Query("SELECT new com.pllis.mylog.dto.MyBookListResponseDto(" +
            "M.myBookNo, " +
            "M.bookTitle, " +
            "M.bookImageLink, " +
            "M.readStatus, " +
            "M.collectionType, " +
            "M.totalPage, " +
            "M.readPage, " +
            "M.scope, " +
            "M.registrationDatetime, " +
            "M.updateDatetime )" +
            "FROM MyBook M " +
            "INNER JOIN User U ON U.userNo = M.userNo " +
            "INNER JOIN CommonCode R ON M.readStatus = R.code AND R.useYn = 'Y' " +
            "INNER JOIN CommonCode C ON M.collectionType = C.code AND C.useYn = 'Y' " +
            "WHERE U.userNo = :userNo AND deleteYn='N' AND M.readStatus = :readStatus")
    Page<MyBookListResponseDto> findUserBookListReadStatus(@Param("userNo") Integer userNo,
                                                           Pageable pageable,
                                                           @Param("readStatus") String readStatus);


    //소장 유형별 조회
    @Query("SELECT new com.pllis.mylog.dto.MyBookListResponseDto(" +
            "M.myBookNo, " +
            "M.bookTitle, " +
            "M.bookImageLink, " +
            "M.readStatus, " +
            "M.collectionType, " +
            "M.totalPage, " +
            "M.readPage, " +
            "M.scope, " +
            "M.registrationDatetime, " +
            "M.updateDatetime )" +
            "FROM MyBook M " +
            "INNER JOIN User U ON U.userNo = M.userNo " +
            "INNER JOIN CommonCode R ON M.readStatus = R.code AND R.useYn = 'Y' " +
            "INNER JOIN CommonCode C ON M.collectionType = C.code AND C.useYn = 'Y' " +
            "WHERE U.userNo = :userNo AND deleteYn='N' AND M.collectionType = :collectionType")
    Page<MyBookListResponseDto> findUserBookListCollectionType(@Param("userNo") Integer userNo,
                                                               Pageable pageable,
                                                               @Param("collectionType") String collectionType);

    //독서 상태 & 소장 유형별
    @Query("SELECT new com.pllis.mylog.dto.MyBookListResponseDto(" +
            "M.myBookNo, " +
            "M.bookTitle, " +
            "M.bookImageLink, " +
            "M.readStatus, " +
            "M.collectionType, " +
            "M.totalPage, " +
            "M.readPage, " +
            "M.scope, " +
            "M.registrationDatetime, " +
            "M.updateDatetime )" +
            "FROM MyBook M " +
            "INNER JOIN User U ON U.userNo = M.userNo " +
            "INNER JOIN CommonCode R ON M.readStatus = R.code AND R.useYn = 'Y' " +
            "INNER JOIN CommonCode C ON M.collectionType = C.code AND C.useYn = 'Y' " +
            "WHERE U.userNo = :userNo AND M.readStatus = :readStatus AND M.collectionType = :collectionType")
    Page<MyBookListResponseDto> findUserBookListReadStatusAndCollectionType(@Param("userNo") Integer userNo,
                                                                            Pageable pageable,
                                                                            @Param("readStatus") String readStatus,
                                                                            @Param("collectionType") String collectionType);
}
