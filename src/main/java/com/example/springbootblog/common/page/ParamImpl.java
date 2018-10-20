package com.example.springbootblog.common.page;


import com.example.springbootblog.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 参数实现类
 * Created by zhoul on 2017/11/8.
 */
public class ParamImpl implements IParameter {

    private Map<String,Object> param;

    public ParamImpl(Map<String,Object> param) {
        this.param = param;
    }

    @Override
    public <T> T get(String paramName) {
        return (T) param.get(paramName);
    }

    public <T> T getPara(String name) {
        return (T) get(name);
    }

    /**
     * Returns the value of a request parameter and convert to Integer.
     *
     * @param name a String specifying the name of the parameter
     * @return a Integer representing the single value of the parameter
     */
    public Integer getParaToInt(String name) {
        return getParaToInt(name, null);
    }

    /**
     * Returns the value of a request parameter and convert to Integer with a default value if it is null.
     *
     * @param name a String specifying the name of the parameter
     * @return a Integer representing the single value of the parameter
     */
    public Integer getParaToInt(String name, Integer defaultValue) {
        Object value = getPara(name);
        Integer para = null;
        if(value instanceof String){
            para = Integer.parseInt(value.toString());
        }else if(value instanceof Integer){
            para = (Integer) value;
        }
        if(para == null){
            para = defaultValue;
        }
        return para;
    }



    public Long getParaToLong(String name) {
        return getParaToLong((String) getPara(name), null);
    }

    /**
     * Returns the value of a request parameter and convert to Long with a default value if it is null.
     *
     * @param name a String specifying the name of the parameter
     * @return a Integer representing the single value of the parameter
     */
    public Long getParaToLong(String name, Long defaultValue) {
        Long value = getPara(name);
        if(value==null){
            return 0L;
        }
        return value;
    }

    private Boolean toBoolean(String value, Boolean defaultValue) {
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        value = value.trim().toLowerCase();
        if ("1".equals(value) || "true".equals(value)) {
            return Boolean.TRUE;
        }else if ("0".equals(value) || "false".equals(value)){
            return Boolean.FALSE;
        }
        throw new RuntimeException("Can not parse the parameter \"" + value + "\" to Boolean value.");
    }

    /**
     * Returns the value of a request parameter and convert to Boolean.
     *
     * @param name a String specifying the name of the parameter
     * @return true if the value of the parameter is "true" or "1", false if it is "false" or "0", null if parameter is not exists
     */
    public Boolean getParaToBoolean(String name) {
        return toBoolean((String) getPara(name), null);
    }

    /**
     * Returns the value of a request parameter and convert to Boolean with a default value if it is null.
     *
     * @param name a String specifying the name of the parameter
     * @return true if the value of the parameter is "true" or "1", false if it is "false" or "0", default value if it is null
     */
    public Boolean getParaToBoolean(String name, Boolean defaultValue) {
        return toBoolean((String) getPara(name), defaultValue);
    }


    private Date toDate(String value, Date defaultValue) {
        try {
            if (StringUtils.isBlank(value))
                return defaultValue;
            return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(value.trim());
        } catch (Exception e) {
            throw new RuntimeException("Can not parse the parameter \"" + value + "\" to Date value.");
        }
    }

    /**
     * Returns the value of a request parameter and convert to Date.
     *
     * @param name a String specifying the name of the parameter
     * @return a Date representing the single value of the parameter
     */
    public Date getParaToDate(String name) {
        return toDate((String) getPara(name), null);
    }

    /**
     * Returns the value of a request parameter and convert to Date with a default value if it is null.
     *
     * @param name a String specifying the name of the parameter
     * @return a Date representing the single value of the parameter
     */
    public Date getParaToDate(String name, Date defaultValue) {
        return toDate((String) getPara(name), defaultValue);
    }


    private Integer toInt(String value, Integer defaultValue) {
        try {
            if (StringUtils.isBlank(value))
                return defaultValue;
            value = value.trim();
            if (value.startsWith("N") || value.startsWith("n"))
                return -Integer.parseInt(value.substring(1));
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new RuntimeException("Can not parse the parameter \"" + value + "\" to Integer value.");
        }
    }

    /**
     * Returns the value of a request parameter as a String, or default value if the parameter does not exist.
     *
     * @param name         a String specifying the name of the parameter
     * @param defaultValue a String value be returned when the value of parameter is null
     * @return a String representing the single value of the parameter
     */
    public String getPara(String name, String defaultValue) {
        String result = getPara(name);
        return result != null && !"".equals(result) ? result : defaultValue;
    }


    /**
     * Returns an array of String objects containing all of the values the given request
     * parameter has, or null if the parameter does not exist. If the parameter has a
     * single value, the array has a length of 1.
     *
     * @param name a String containing the name of the parameter whose value is requested
     * @return an array of String objects containing the parameter's values
     */
    public String[] getParaValues(String name) {
        return getPara(name);
    }

    /**
     * Returns an array of Integer objects containing all of the values the given request
     * parameter has, or null if the parameter does not exist. If the parameter has a
     * single value, the array has a length of 1.
     *
     * @param name a String containing the name of the parameter whose value is requested
     * @return an array of Integer objects containing the parameter's values
     */
    public Integer[] getParaValuesToInt(String name) {
        String[] values = getPara(name);
        if (values == null)
            return null;
        Integer[] result = new Integer[values.length];
        for (int i = 0; i < result.length; i++)
            result[i] = Integer.parseInt(values[i]);
        return result;
    }

    public Long[] getParaValuesToLong(String name) {
        String[] values = getPara(name);
        if (values == null)
            return null;
        Long[] result = new Long[values.length];
        for (int i = 0; i < result.length; i++)
            result[i] = Long.parseLong(values[i]);
        return result;
    }

    public <T> List<T> getList(String name) {
        return getPara(name);
    }
}
