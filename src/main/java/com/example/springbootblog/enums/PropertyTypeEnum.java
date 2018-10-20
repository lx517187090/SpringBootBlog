package com.example.springbootblog.enums;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 属性数据类型
 * Created by zhouj on 2017/5/16.
 */
public enum PropertyTypeEnum {
    S(String.class), I(Integer.class), L(Long.class), G(BigDecimal.class), N(Double.class), D(Date.class), B(Boolean.class);

    Class<?> value;

    PropertyTypeEnum(Class<?> value) {
        this.value = value;
    }

    public Class<?> getValue() {
        return value;
    }

    public static PropertyTypeEnum valueOf(Class<?> value) {
        for(PropertyTypeEnum propertyTypeEnum : PropertyTypeEnum.values()){
            if(propertyTypeEnum.getValue().getName().equalsIgnoreCase(value.getName())){
                return propertyTypeEnum;
            }
        }
        return null;
    }
}
