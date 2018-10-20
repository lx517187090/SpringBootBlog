package com.example.springbootblog.common.page;

/**
 * 页面返回实体适配器
 * Created by zhoul on 2017/9/19.
 */
public interface IPageAdapter<B> {
    <T> void convert(IPagePara pagePara, IPageResult<T> pageResult);
}
