package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 肖德子裕
 * @date: 2018/11/22 19:30
 * @description:
 */
@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     *
     * @return
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 添加
     *
     * @param label
     * @return
     */
    public void save(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 修改
     *
     * @param label
     * @return
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 根据查询条件查询
     *
     * @param label
     * @return
     */
    public List<Label> search(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            /**
             * 查询条件的封装
             * @param root 根对象，就是要封装到哪个对象
             * @param query 查询条件
             * @param cb 封装过程
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //创建一个集合用于封装条件（因为集合长度无法确定）
                List<Predicate> list = new ArrayList<>();

                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    //创建查询条件
                    Predicate predicate = cb.like(root.get("labelname").as(String.class),
                            "%" + label.getLabelname() + "%");
                    //封装到list
                    list.add(predicate);

                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    //创建查询条件
                    Predicate predicate = cb.like(root.get("state").as(String.class),label.getState());
                    //封装到list
                    list.add(predicate);

                }

                //创建一个数组
                Predicate[] parr=new Predicate[list.size()];
                //将list中条件全部放到数组
                list.toArray(parr);
                //返回所有查询条件
                return cb.and(parr);
            }
        });
    }

    /**
     * 分页查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page<Label> pageQuery(Label label, int page, int size) {
        //封装一个springdatajpa自带分页对象
        Pageable pageable = PageRequest.of(page-1,size);

        return labelDao.findAll(new Specification<Label>() {
            /**
             * 查询条件的封装
             *
             * @param root  根对象，就是要封装到哪个对象
             * @param query 查询条件
             * @param cb    封装过程
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //创建一个集合用于封装条件（因为集合长度无法确定）
                List<Predicate> list = new ArrayList<>();

                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    //创建查询条件
                    Predicate predicate = cb.like(root.get("labelname").as(String.class),
                            "%" + label.getLabelname() + "%");
                    //封装到list
                    list.add(predicate);

                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    //创建查询条件
                    Predicate predicate = cb.like(root.get("state").as(String.class), label.getState());
                    //封装到list
                    list.add(predicate);

                }

                //创建一个数组
                Predicate[] parr = new Predicate[list.size()];
                //将list中条件全部放到数组
                list.toArray(parr);
                //返回所有查询条件
                return cb.and(parr);
            }
        },pageable);
    }
}
