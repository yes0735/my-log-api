package com.pllis.mylog.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 책기록 테이블
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "BOOK_LOG")
public class BookLog {
    @Id
    @GeneratedValue
    @Column(name = "BOOK_LOG_NO")
    private Integer bookLogNo;
    @Column(name = "MY_BOOK_NO")
    private Integer myBookNo;
    @Column(name = "CONTENTS")
    private String contents;
    @Column(name = "REGISTRATION_DATETIME")
    private Date registrationDatetime;
    @Column(name = "UPDATE_DATETIME")
    private Date updateDatetime;
    @Column(name = "DELETE_YN")
    @Enumerated(EnumType.STRING)
    private DeleteStatus deleteYn;
    public enum DeleteStatus {
        Y, N
    }
    @Column(name = "DELETE_DATETIME")
    private Date deleteDatetime;
}
