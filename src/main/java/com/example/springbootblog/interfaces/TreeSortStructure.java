package com.example.springbootblog.interfaces;

/**
 * 带有层级结构且排序树形接口
 * Created by zhengg on 2017/11/20.
 */
public interface TreeSortStructure<M> extends TreeStructureAttribute<M> {
    Integer getSort();
}
