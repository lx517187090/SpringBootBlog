package com.example.springbootblog.dao;

import com.example.springbootblog.entity.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface BlogDao extends ElasticsearchRepository<EsBlog, String> {

   /* *//**
     * 分页查询
     * @param title
     * @param summary
     * @param content
     * @return
     *//*
    Page<EsBlog> findDistinctEsBlogByTitleContainOrSummaryContainOrContentContain(String title, String summary, String content);*/
}
