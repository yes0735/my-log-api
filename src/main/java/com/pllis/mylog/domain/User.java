package com.pllis.mylog.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 테이블
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "USER_NO")
    private Integer userNo;
    @Column(name = "USER_MAIL")
    private String userMail;
    @Column(name = "USER_NICKNAME")
    private String userNickname;
    @Column(name = "USER_PASSWORD")
    private String userPassword;
    @Column(name = "REGISTRATION_DATETIME")
    private Date registrationDatetime;
    @Column(name = "UPDATE_DATETIME")
    private Date updateDatetime;
    @Column(name = "USER_STATUS")
    private String userStatus;
}
