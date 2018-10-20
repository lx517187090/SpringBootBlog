package com.example.springbootblog.common.page;

/**
 * 提供BaseDao层获取页面接口
 * Created by zhoul on 2017/9/18.
 */
public interface IPagePara {

    /**
     * 显示第几页
     * @return
     */
    Integer getPageNumber();

    /**
     * 每页的大小
     * @return
     */
    Integer getPageSize();

    String getCurrentUserId();

//    String getOrderCondition();
}
