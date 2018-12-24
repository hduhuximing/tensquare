package com.tensquare.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @author: 肖德子裕
 * @date: 2018/11/27 20:28
 * @description: 将文章添加至索引库
 */
@Document(indexName = "tensquare_article",type = "article")
public class Article implements Serializable {
    @Id
    private String id;

    /**
     * 是否索引：表示该域是否能被搜索
     * 是否分词：表示搜索时是整体匹配还是分词匹配
     * 是否显示：表示是否在页面上显示该域的内容
     * index = true：创建索引
     * analyzer = "ik_max_word"：按最细分词
     * searchAnalyzer = "ik_max_word"：按最细搜索
     */
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;

    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;

    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
