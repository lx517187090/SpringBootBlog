package com.example.springbootblog.common.page;

import java.util.Date;
import java.util.List;

/**
 * 参数接口
 * Created by zhoul on 2017/11/8.
 */
public interface IParameter {
    <T> T get(String paramName);

    <T> T getPara(String name);

    String getPara(String name, String defaultValue);

    String[] getParaValues(String name);

    Integer[] getParaValuesToInt(String name);

    Long[] getParaValuesToLong(String name);

    Integer getParaToInt(String name);

    Integer getParaToInt(String name, Integer defaultValue);

    Long getParaToLong(String name);

    Long getParaToLong(String name, Long defaultValue);

    Boolean getParaToBoolean(String name);

    Boolean getParaToBoolean(String name, Boolean defaultValue);

    Date getParaToDate(String name);

    Date getParaToDate(String name, Date defaultValue);

    <T> List<T> getList(String name);
}
