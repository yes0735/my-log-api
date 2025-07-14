package com.pllis.mylog.service;

import com.pllis.mylog.domain.DeleteStatus;
import com.pllis.mylog.dto.MyBookListResponseDto;
import com.pllis.mylog.repository.BookLogRepository;
import com.pllis.mylog.repository.BookRepository;
import com.pllis.mylog.repository.MyBookListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    public final BookRepository bookRepository;
    public final MyBookListRepository myBookListRepository;
    public final BookLogRepository bookLogRepository;
    /**
     * 사용자가 등록한 책 불러오기(4가지 필터 지원)
     *  - 기본 조회(최근 등록된 모든 책 목록)
     *  - 독서 상태(탭)별 조회
     *  - 소장 유형(ebook, 종이책 등) 별 조회
     *  - 독서 상태 + 소장 유형 동시 필터링
     * @param userNo 사용자 번호
     * @param pageable 페이지 정보(Page 번호, 크기, 정렬)
     * @param readStatus 독서 상태(예:reading,finished 등)
     * @param collectionType 소장 유형 필터(예:ebook,paper_book등)
     * @return 필터링 된 책 목록(페이지 형태)
     **/
    public Page<MyBookListResponseDto> getMyBookListResponseDtos(Integer userNo, Pageable pageable, String readStatus, String collectionType) {

        boolean noReadStatus = readStatus == null || readStatus.equals("null");
        boolean noCollectionType = collectionType == null || collectionType.equals("null");

        //기본 조회
        if (noReadStatus && noCollectionType) { 
            return myBookListRepository.findUserBookList(userNo, pageable);
        }

        //noReadStatus만 있을때(탭)
        if (!noReadStatus && noCollectionType) {
            return myBookListRepository.findUserBookListReadStatus(userNo, pageable, readStatus);
        }

        //noCollectionType만 있을 때(소장 유형별)
        if (noReadStatus && !noCollectionType) {
            log.info(collectionType);
            return myBookListRepository.findUserBookListCollectionType(userNo, pageable, collectionType);
        }

        //둘 다 있을 때
        return myBookListRepository.findUserBookListReadStatusAndCollectionType(userNo, pageable, readStatus,collectionType);

    }

    @Transactional
    public void softDeleteByMyBook(Integer myBookNo){
        try{
            bookLogRepository.softDeleteByBookLog(myBookNo, DeleteStatus.Y);
            bookRepository.softDeleteByMyBook(myBookNo,DeleteStatus.Y);
        }catch (Exception e){
            log.error("마이 책 삭제 실패, myBook{}", myBookNo, e);
            throw new RuntimeException("마이 책 삭제 중 오류 발생",e);
        }

    }

}