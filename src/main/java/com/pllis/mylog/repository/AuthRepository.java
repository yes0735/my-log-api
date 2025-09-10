package com.pllis.mylog.repository;

import com.pllis.mylog.domain.MyBook;
import com.pllis.mylog.domain.User;
import com.pllis.mylog.dto.MyBookListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {
}