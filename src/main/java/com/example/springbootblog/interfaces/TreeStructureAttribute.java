package com.example.springbootblog.interfaces;

/**
 * 带有层级结构树形接口，附带更多的属性
 * Created by zhoul on 2017/10/26.
 */
public interface TreeStructureAttribute<M> extends TreeStructure<M> {

    Boolean getExpanded();

    void setExpanded(Boolean expanded);

    Boolean getIsLeaf();

    void setIsLeaf(Boolean leaf);

    void setLevel(Integer level);

    Integer getLevel();
}
