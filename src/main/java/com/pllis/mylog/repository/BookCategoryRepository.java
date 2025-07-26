package com.pllis.mylog.repository;

import com.pllis.mylog.domain.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface BookCategoryRepository extends JpaRepository<BookCategory,Integer> {
}
