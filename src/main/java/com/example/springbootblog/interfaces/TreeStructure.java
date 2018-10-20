package com.example.springbootblog.interfaces;

import java.util.List;

/**
 * 带有层级结构树形接口
 * Created by zhoul on 2017/9/6.
 */
public interface TreeStructure<M> extends TreeList {

    void setChildren(List<M> object);

    List<M> getChildren();
}
