package com.example.springbootblog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 网点，单位注入
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Corp {
	String value() default "";
	/**
	 * 填写的值是被注入，该属性必须为String类型
	 * @return
	 */
	String inject() default "";

	/**
	 * Corp实体中的属性
	 * @return
	 */
	String field() default "";

}
