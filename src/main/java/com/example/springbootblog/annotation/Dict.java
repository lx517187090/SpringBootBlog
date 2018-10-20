package com.example.springbootblog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基础字典、数据字典国际化
 * 周亮
 * 2017-06-01
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {

	/**
	 * 数据字典：需要国际化的表的名称
	 * @return
	 */
	String tableName() default "";

	/**
	 * 数据字典：需要国际化的字段值的名称
	 * @return
	 */
	String valueFieldName() default "";

	/**
	 * 数据字典：要显示名称的字段名
	 * @return
	 */
	String showFiledName() default "";



	/**
	 * 基础字典组
	 * @return
	 */
	String dictGroup() default "";


	/**
	 * 填写的值是被注入国际化的属性，该属性必须为String类型
	 * @return
	 */
	String inject();

}
