package com.example.springbootblog.interfaces;

import java.io.Serializable;

/**
 * 没有层级结构的顺序树形排序集合列表
 * Created by zhoul on 2017/9/6.
 */
public interface TreeList extends Serializable {
    String getId();

    String getParentId();
}
