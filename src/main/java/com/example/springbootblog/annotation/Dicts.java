package com.example.springbootblog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被注解的属性可以向当前类中的多个属性注入国际化内容
 * 周亮
 * 2017-06-19
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dicts {

	/**
	 * 数据字典：需要国际化的表的名称
	 * @return
	 */
	Dict[] value();

}
