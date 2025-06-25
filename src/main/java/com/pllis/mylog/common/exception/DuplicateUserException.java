package com.pllis.mylog.common.exception;

import lombok.Getter;

@Getter
public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String message) {
        super(message);
    }
}