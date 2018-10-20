package com.example.springbootblog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 银行联行号注入
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InputBankInfos {

	String valField() default "bankCode";
	
	/**
	 * 数据字典：需要国际化的表的名称
	 * @return
	 */
	InputBankInfo[] value();

}
