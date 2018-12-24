package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author: 肖德子裕
 * @date: 2018/11/27 21:01
 * @description:
 */
public interface ArticleDao extends ElasticsearchRepository<Article,String> {
    /**
     * 根据关键字搜索文章
     * @param title 标题
     * @param content 内容
     * @param pageable 分页
     * @return 分页对象
     */
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
