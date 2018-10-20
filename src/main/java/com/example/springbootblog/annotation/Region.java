package com.example.springbootblog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 区域注入
 * <b>作者：</b>周亮
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Region {

	/**
	 * 填写的值是被注入属性名，该属性必须为String类型
	 * @return
	 */
	String inject();

}
