package com.pllis.mylog.repository;

import com.pllis.mylog.domain.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthRepository extends JpaRepository<MyBook, Long> {

}