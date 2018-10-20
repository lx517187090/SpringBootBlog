package com.example.springbootblog.annotation;

import com.example.springbootblog.annotation.impl.ByteRangeLengthImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 */
//@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ByteRangeLengthImpl.class)
public @interface ByteRangeLength {
    //字节长度值
    int value() default 0;

    String message() default "length is to long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ByteRangeLength[] value();
    }
}
