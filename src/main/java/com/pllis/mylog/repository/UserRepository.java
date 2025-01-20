package com.pllis.mylog.repository;

import com.pllis.mylog.domain.MyBook;
import com.pllis.mylog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserMail(String email);

}