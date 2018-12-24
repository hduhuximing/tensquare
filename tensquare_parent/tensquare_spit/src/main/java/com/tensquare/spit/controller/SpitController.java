package com.tensquare.spit.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 肖德子裕
 * @date: 2018/11/27 8:14
 * @description:
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 吐槽点赞
     * 控制用户重复点赞
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId){
        //判断当前用户是否已经点赞
        String userid="111";
        //如果该用户已经点赞
        if (redisTemplate.opsForValue().get("thumbup_"+userid)!=null){
            return new Result(false, StatusCode.REPERROR,"不能重复点赞");
        }
        spitService.thumbup(spitId);
        //将用户保存到redis
        redisTemplate.opsForValue().set("thumbup_"+userid,1);
        return new Result(true, StatusCode.OK,"点赞成功");
    }

    /**
     * 根据父节点查询吐槽（就是说一个人的吐槽下面会跟着其他人的吐槽）；并且分页显示
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,@PathVariable int page,
                                     @PathVariable int size){
        Page<Spit> pageData = spitService.findByParentid(parentid, page, size);
        return new Result(true,StatusCode.OK,"查询成功",
                new PageResult<Spit>(pageData.getTotalElements(),pageData.getContent()));
    }

    /**
     * 查询所有吐槽
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    /**
     * 数据ID查询吐槽
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findById(spitId));
    }

    /**
     * 保存吐槽信息
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"保存成功");
    }

    /**
     * 修改吐槽信息
     * @param spitId
     * @param spit
     * @return
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId,@RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 根据ID删除吐槽
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
