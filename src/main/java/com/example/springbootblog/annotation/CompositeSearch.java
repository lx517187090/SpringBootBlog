package com.example.springbootblog.annotation;

import java.lang.annotation.*;

/**
 * where查询处理注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
@Documented
public @interface CompositeSearch {
    String[] fieldName() default "";
    String[] operCode() default "";
    String orderBy() default "";
}
