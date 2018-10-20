package com.example.springbootblog.common.exception;


import com.example.springbootblog.enums.IMessageEnum;

/**
 * 业务异常定义类
 * @author 周亮
 */
public class BusinessException extends AbstractBytterException {
    private static final long serialVersionUID = 1L;


    /**
     * 向外层抛出枚举业务异常错误
     * @param IMessageEnum
     */
    public BusinessException(IMessageEnum IMessageEnum) {
        super(IMessageEnum);
    }

    /**
     * 向外层抛出枚举业务异常错误
     * @param IMessageEnum 枚举错误描述
     * @param params    替换参数
     */
    public BusinessException(IMessageEnum IMessageEnum, String ... params) {
        super(IMessageEnum, params);
    }

    /**
     * 抛出错误信息，不建议使用
     * @param message
     */
    @Deprecated
    public BusinessException(String message) {
        super(message);
    }

    @Deprecated
    public BusinessException(Throwable cause) {
        super(cause);
    }

    @Deprecated
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
