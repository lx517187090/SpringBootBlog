package com.example.springbootblog.interfaces;

/**
 * 用于树形结构自定义搜索接口，返回命中的所有子数据（包含自身）
 * Created by zhoul on 2017/9/30.
 */
public interface TreeMatcher<T> {


    /**
     * 命中方法
     * @param o1    <T>对象
     * @return  true命中，false，不命中
     */
    boolean hit(T o1);
}
