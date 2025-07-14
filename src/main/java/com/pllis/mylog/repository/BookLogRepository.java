package com.pllis.mylog.repository;

import com.pllis.mylog.domain.BookLog;
import com.pllis.mylog.domain.DeleteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookLogRepository extends JpaRepository<BookLog, Integer> {
    //책 기록 삭제 처리
    @Modifying
    @Query("UPDATE BookLog b " +
            "SET b.deleteYn = :deleteStatus, " +
            "b.deleteDatetime = CURRENT_TIMESTAMP " +
            "WHERE b.myBookNo = :myBookNo AND b.deleteYn = 'N'")
    void softDeleteByBookLog(@Param("myBookNo") Integer bookNo ,
                             @Param("deleteStatus") DeleteStatus deleteStatus);
}
