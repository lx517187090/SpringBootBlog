package com.example.springbootblog.common.page;

import java.util.List;

/**
 * 查询出的结果集
 * Created by zhoul on 2017/9/18.
 */
public interface IPageResult<T> {


    List<T> getList();

    void setList(List<T> list);

    Integer getPageNumber();

    void setPageNumber(Integer pageNumber);

    Integer getTotalRow();

    void setTotalRow(Integer totalRow);

    IPagePara getPagePara();

    void setPagePara(IPagePara iPagePara);

    <C extends IPageAdapter> C convert(Class<? extends IPageAdapter> adapter);


}
