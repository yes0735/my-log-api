package com.pllis.mylog.repository;

import com.pllis.mylog.domain.DeleteStatus;
import com.pllis.mylog.domain.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<MyBook, Integer> {  // JpaRepository로 변경
    //나의 책 삭제 처리
    @Modifying
    @Query("UPDATE MyBook m " +
            "SET m.deleteYn = :deleteStatus, " +
            "m.deleteDatetime = CURRENT_TIMESTAMP " +
            "WHERE m.myBookNo = :myBookNo AND m.deleteYn='N'")
    void softDeleteByMyBook(@Param("myBookNo")Integer myBookNo,
                            @Param("deleteStatus") DeleteStatus deleteStatus);


}


