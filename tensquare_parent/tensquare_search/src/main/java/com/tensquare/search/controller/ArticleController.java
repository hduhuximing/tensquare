package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 肖德子裕
 * @date: 2018/11/27 21:08
 * @description:
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 保存文章相关信息到索引库
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.save(article);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 根据关键字搜索文章
     * @param key 关键字
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/{key}/{page}/{size}",method = RequestMethod.GET)
    public Result findByKey(@PathVariable String key,@PathVariable int page,
                            @PathVariable int size){
        Page<Article> pageData=articleService.findByKey(key,page,size);
        return new Result(true, StatusCode.OK,"查询成功",
                new PageResult<Article>(pageData.getTotalElements(),pageData.getContent()));
    }
}
