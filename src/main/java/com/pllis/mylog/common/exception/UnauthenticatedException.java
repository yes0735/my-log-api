package com.pllis.mylog.common.exception;

import lombok.extern.slf4j.Slf4j;

public class UnauthenticatedException extends RuntimeException{
    public UnauthenticatedException(String message) {
        super(message);
    }
}
