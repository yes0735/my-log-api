package com.pllis.mylog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pllis.mylog.domain.MyBook;


@Repository
public interface BookRepository extends JpaRepository<MyBook, Long> {

}