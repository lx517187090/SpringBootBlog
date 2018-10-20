package com.example.springbootblog.injections;

import java.util.List;

/**
 * Created by zhoul on 2017/5/22.
 */
public interface IInjection {

    void inject(List<? extends IBaseDataInject> list);
    void inject(IBaseDataInject object);

    @Deprecated
    void searchInject(List<?> source, Class<?> clazz);
    @Deprecated
    void searchInject(Object obj, Class<?> clazz);
}
