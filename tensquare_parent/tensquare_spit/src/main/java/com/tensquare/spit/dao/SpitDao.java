package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: 肖德子裕
 * @date: 2018/11/26 21:20
 * @description:
 */
public interface SpitDao extends MongoRepository<Spit,String> {
    /**
     * 根据父节点查询吐槽（就是说一个人的吐槽下面会跟着其他人的吐槽）；并且分页显示
     * @param parentid
     * @param pageable
     * @return
     */
    public Page<Spit> findByParentid(String parentid, Pageable pageable);
}
