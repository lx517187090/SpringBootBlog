package com.example.springbootblog.common.exception;

/**
 * Created by zhoul on 2017/6/1.
 */
public class InjectionException extends RuntimeException {

    public InjectionException(String message) {
        super(message);
    }

    public InjectionException(Throwable cause) {
        super(cause);
    }

    public InjectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
