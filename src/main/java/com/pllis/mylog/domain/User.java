package com.pllis.mylog.domain;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 사용자 테이블
 */
@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO")
    private Integer userNo;

    @Column(name = "USER_MAIL")
    private String userMail;

    @Column(name = "USER_NICKNAME")
    private String userNickname;

    @Column(name = "USER_PASSWORD")
    private String userPassword;

    @Column(name = "USER_MOBILE_PHONE_NUMBER")
    private String userMobilePhoneNumber;

    @CreatedDate
    @Column(name = "REGISTRATION_DATETIME")
    private Date registrationDatetime;

    @LastModifiedDate
    @Column(name = "UPDATE_DATETIME")
    private Date updateDatetime;

    @Column(name = "USER_STATUS")
    private String userStatus;

    @Builder
    public User(String userMail, String userPassword, String userNickname, String userMobilePhoneNumber, String userStatus) {
        this.userMail = userMail;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userMobilePhoneNumber = userMobilePhoneNumber;
        this.userStatus = userStatus;
    }
}
