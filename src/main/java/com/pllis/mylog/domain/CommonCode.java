package com.pllis.mylog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 공통 코드 테이블
 */
@NoArgsConstructor
@Entity
@Getter
@Table(name="COMMON_CODE")
public class CommonCode {
    @Id
    @Column(name="CODE")
    private String code;

    // nullable = false -> NOT NULL
    @Column(name="CODE_NAME", nullable = false)
    private String codeName;

    @Column(name="CODE_DESCRIPTION")
    private String codeDescription;

    @Column(name="USE_YN", nullable = false)
    private String useYn = "Y";

    //updatable = false -> 수정 금지
    @Temporal(TemporalType.TIMESTAMP) //Date 타입에 쓰는게 좋음
    @Column(name="REGISTRATION_DATETIME", nullable = false, updatable = false)
    private Date registrationDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATE_DATETIME")
    private Date updateDatetime;
}
