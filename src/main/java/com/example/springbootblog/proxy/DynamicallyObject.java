package com.example.springbootblog.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouj on 2017/7/26.
 */
public class DynamicallyObject {
    private Map<String,Object> params = new HashMap<String, Object>();

    public DynamicallyObject(){
        super();
    }

    private DynamicallyObject(Map<String, Object> map){
        super();
        this.params = map;
    }

    public static DynamicallyObject getInstance(Map<String,Object> map) {
        return new DynamicallyObject(map);
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
