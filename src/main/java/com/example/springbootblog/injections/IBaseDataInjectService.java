package com.example.springbootblog.injections;

import java.lang.annotation.Annotation;

/**
 * Created by zhoul on 2017/12/11.
 */
interface IBaseDataInjectService {

    /**
     * 获取多注解中的方法
     *
     * @param annotation
     * @return
     */
    Annotation[] getMultiInjectAnnotations(Annotation annotation);


    /**
     * 交给子类返回需要被注入属性的名称
     *
     * @param annotation
     * @return
     */
    String getInjectAttributeAnnotation(Annotation annotation);

    /**
     * 交给子类通过注解解析返回值
     *
     * @param value
     * @param annotation
     * @return
     */
    String returnInjectValue(String InjectFieldName, Object value, Annotation annotation);
}
