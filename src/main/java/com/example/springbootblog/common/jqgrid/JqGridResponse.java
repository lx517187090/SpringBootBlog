package com.example.springbootblog.common.jqgrid;

import java.io.Serializable;
import java.util.List;


public class JqGridResponse<T extends Serializable> {


    protected List<T> rows;

    public JqGridResponse() {
    }

    public JqGridResponse(List<T> rows) {
        this.rows = rows;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}