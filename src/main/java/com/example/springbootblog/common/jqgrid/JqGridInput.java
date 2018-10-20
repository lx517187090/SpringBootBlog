package com.example.springbootblog.common.jqgrid;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhoul on 2017/7/25.
 */
public class JqGridInput {

    @JsonProperty("_search")
    private Boolean search;
    private String filters;
    private Integer page;
    private Integer rows;
    private String sidx;
    private  String sord;

    public Boolean getSearch() {
        return search;
    }

    public void setSearch(Boolean search) {
        this.search = search;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }
}
