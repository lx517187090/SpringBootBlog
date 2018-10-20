package com.example.springbootblog.enums;

/**
 * 枚举接口类，用于实现业务消息国际化，方便统一异常处理。
 * 代码定义：代码长度共6位，格式为X11222，结构如下
 * X：系统定义的错误代码，开发时无需填写。
 * 11：所属业务模块的子业务代码，从0开始
 * 222：消息代码
 * @author 周亮
 */
public interface IMessageEnum {
    /**
     * 返回自己枚举对象
     * @return this
     */
    Enum getEnum();

    /**
     * 获取枚举的错误代码
     * @return
     */
    String getCode();
}
