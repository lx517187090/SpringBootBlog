package com.example.springbootblog.common.exception;


import com.example.springbootblog.enums.IMessageEnum;

/**
 */
public class AbstractBytterException extends RuntimeException {

    private IMessageEnum IMessageEnum;

    private String[] params;

    public AbstractBytterException(IMessageEnum IMessageEnum) {
        this.IMessageEnum = IMessageEnum;
    }

    public AbstractBytterException(IMessageEnum IMessageEnum,String ... params) {
        this.IMessageEnum = IMessageEnum;
        this.params = params;
    }

    public IMessageEnum getIMessageEnum() {
        return IMessageEnum;
    }

    public AbstractBytterException(String message) {
        super(message);
    }

    public AbstractBytterException(Throwable cause) {
        super(cause);
    }

    public AbstractBytterException(String message, Throwable cause) {
        super(message, cause);
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
