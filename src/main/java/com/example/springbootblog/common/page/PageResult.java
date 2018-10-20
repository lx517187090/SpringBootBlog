package com.example.springbootblog.common.page;

import org.springframework.util.Assert;

import java.util.List;

/**
 * 查询结果集
 * Created by zhoul on 2017/9/18.
 */
public class PageResult<T> implements IPageResult<T> {
    /**
     * 结果集
     */
    protected List<T> list;
    /**
     * 当前页码
     */
    protected Integer pageNumber;
    /**
     * 总共有多少行
     */
    protected Integer totalRow;

    protected IPagePara pagePara;

    public PageResult(IPagePara pagePara, Integer totalRow, List<T> list) {
        setPageNumber(pagePara.getPageNumber());
        setPagePara(pagePara);
        setTotalRow(totalRow);
        setList(list);
    }


    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public Integer getPageNumber() {
        return pageNumber;
    }

    @Override
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public Integer getTotalRow() {
        return totalRow;
    }

    @Override
    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    @Override
    public IPagePara getPagePara() {
        return pagePara;
    }

    @Override
    public void setPagePara(IPagePara pagePara) {
        this.pagePara = pagePara;
    }

    @Override
    public <C extends IPageAdapter> C convert(Class<? extends IPageAdapter> adapter) {
        Assert.notNull(adapter, "adapter must not be null");
        try {
            IPageAdapter adapterInstance = adapter.newInstance();
            adapterInstance.convert(pagePara, this);
            return (C) adapterInstance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


//    @Override
//    public <C extends IPageAdapter, B> C convert(Class<? extends IPageAdapter<B>> adapter) {
//        Assert.notNull(adapter, "adapter must not be null");
//        try {
//            IPageAdapter<B> adapterInstance = adapter.newInstance();
//            adapterInstance.convert(pagePara, this);
//            return (C) adapterInstance;
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


//    @Override
//    public <C extends IPageAdapter> C convert(Class<C> adapter) {
//        Assert.notNull(adapter, "adapter must not be null");
//        try {
//            IPageAdapter adapterInstance = adapter.newInstance();
//            adapterInstance.convert(pagePara, this);
//            return (C) adapterInstance;
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public <B> B convert(IPageAdapter<B> adapter) {
//        adapter.convert(pagePara,this);
//        return (B) adapter;
//    }


}
