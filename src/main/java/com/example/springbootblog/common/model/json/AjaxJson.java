package com.example.springbootblog.common.model.json;

import java.util.Map;

import com.example.springbootblog.enums.IMessageEnum;

/**
 * $.ajax后需要接受的JSON
 *
 * @author
 */
public class AjaxJson {

    private boolean success;// 是否成功
    private String msg;// 提示信息
    private Object obj;// 其他信息
    private Map<String, Object> attributes;// 其他参数
    private IMessageEnum messageEnum;
    private String[] params;

    public AjaxJson() {
    }

    public AjaxJson(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public IMessageEnum getMessageEnum() {
        return messageEnum;
    }

    public void setMessageEnum(IMessageEnum messageEnum) {
        this.messageEnum = messageEnum;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
