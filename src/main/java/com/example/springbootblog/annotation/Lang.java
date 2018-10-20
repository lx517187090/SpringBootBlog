package com.example.springbootblog.annotation;

import java.lang.annotation.*;

/**
 * 字典国际化注解类
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lang {

	/**
	 * 指向需要国际化的实体字典对象
	 * @return
	 */
	Class entity();

	String attrName() default "";


	/**
	 * 该注解写在国际化的值上，填写的值是被注入国际化的属性
	 * @return
	 */
	String inject();

}
