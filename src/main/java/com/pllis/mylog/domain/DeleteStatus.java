package com.pllis.mylog.domain;

/**
 * 나의 책(MyBOOK), 책 기록(BookLog) 삭제 상태
 * - Y : 삭제됨(soft delete)
 * - N : 삭제 되지 않음(기본 상태)
 **/
public enum DeleteStatus {
    Y, N
}