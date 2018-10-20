package com.example.springbootblog.enums;


import com.example.springbootblog.util.StringUtils;

/**
 * 属性比较类型
 * Created by zhouj on 2017/5/16.
 */
public enum MatchTypeEnum {
    EQ("="), LIKE("like"), LLIKE("llike"), RLIKE("rlike"), LT("<"), GT(">"), LE("<="), GE(">="), IN("in") , NE("<>");

    private String value;

    MatchTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return StringUtils.isBlank(value) ? "=" : value;
    }

    public static MatchTypeEnum getTypeName(String value){
        for (MatchTypeEnum matchTypeEnum : MatchTypeEnum.values()){
            if(matchTypeEnum.getValue().equalsIgnoreCase(value)){
                return matchTypeEnum;
            }
        }
        return MatchTypeEnum.EQ;
    }
}
