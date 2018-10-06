package com.example.springbootblog.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.io.Serializable;

/**
 *
 */
@Document(indexName = "blog", type = "blog")
public class EsBlog implements Serializable {

    @Id
    private String id;

    private String titile;

    private String Summary;

    private String content;

    protected EsBlog(){}

    public EsBlog(String id, String titile, String summary, String content) {
        this.id = id;
        this.titile = titile;
        Summary = summary;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EsBlog{" +
                "id='" + id + '\'' +
                ", titile='" + titile + '\'' +
                ", Summary='" + Summary + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
