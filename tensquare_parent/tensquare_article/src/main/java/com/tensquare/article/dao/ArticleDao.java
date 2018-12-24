package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{
    /**
     * 根据ID查询文章并且修改为已审核
     * state=0：未审核；state=1：已审核
     * @param id
     */
    @Modifying
    @Query(value = "update tb_article set state=1 where id=? ",nativeQuery = true)
	public void updateState(String id);

    /**
     * 根据ID查询文章并且点赞
     * thumbup：点赞数量+1
     * @param id
     */
    @Modifying
    @Query(value = "update tb_article set thumbup=thumbup+1 where id=? ",nativeQuery = true)
    public void addThumbup(String id);
}
