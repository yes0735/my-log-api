package com.pllis.mylog.service;

import com.pllis.mylog.dto.UserBookListDto;
import com.pllis.mylog.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    public final BookRepository bookRepository;

    /**
     * 사용자가 등록한 책 불러오기
     *
     * @param userNo 사용자 번호
     * @return 책 리스트
     **/
    public Page<UserBookListDto> getUserBooks(Integer userNo, Pageable pageable, String readStatus, String collectionType) {

        boolean noReadStatus = readStatus == null || readStatus.equals("null");
        boolean noCollectionType = collectionType == null || collectionType.equals("null");

        //기본 조회
        if (noReadStatus && noCollectionType) { 
            return bookRepository.findUserBookList(userNo, pageable, null);
        }

        //noReadStatus만 있을때(탭)
        if (!noReadStatus && noCollectionType) {
            return bookRepository.findUserBookListReadStatus(userNo, pageable, readStatus);
        }

        //noCollectionType만 있을 때(소장 유형별)
        if (noReadStatus && !noCollectionType) {
            log.info(collectionType);
            return bookRepository.findUserBookListCollectionType(userNo, pageable, collectionType);
        }

        //둘 다 있을 때
        return bookRepository.findUserBookListReadStatusAndCollectionType(userNo, pageable, readStatus,collectionType);

    }

}