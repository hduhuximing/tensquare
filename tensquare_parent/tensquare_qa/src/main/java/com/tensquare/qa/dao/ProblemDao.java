package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    /**
     * 最新回答
     * @param labelid 类型分类（JAVA,PHP等）
     * @param pageable 分页
     * @return 问题列表
     */
    @Query(value = "select * from tb_problem,tb_pl id=problemid and labelid=? order by replytime desc",
            nativeQuery = true)
    public Page<Problem> newList(String labelid, Pageable pageable);

    /**
     * 热门回答
     * @param labelid 类型分类（JAVA,PHP等）
     * @param pageable 分页
     * @return 问题列表
     */
    @Query(value = "select * from tb_problem,tb_pl id=problemid and labelid=? order by reply desc",
            nativeQuery = true)
    public Page<Problem> hotList(String labelid, Pageable pageable);

    /**
     * 等待回答
     * @param labelid 类型分类（JAVA,PHP等）
     * @param pageable 分页
     * @return 问题列表
     */
    @Query(value = "select * from tb_problem,tb_pl id=problemid and labelid=? and reply=0 order by createtime desc",
            nativeQuery = true)
    public Page<Problem> waitList(String labelid, Pageable pageable);
}
