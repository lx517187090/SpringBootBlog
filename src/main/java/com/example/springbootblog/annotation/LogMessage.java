package com.example.springbootblog.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD })
@Documented
public @interface LogMessage {
	/** 日志属性映射名称 **/
	String content() default "";
	short level() default 1;//info 1 warring 2 error 3;
	short type() default 0;//0 其他 1 登陆 2 退出 3 插入 4 删除 5 更新 6 上传 7 查询
}
