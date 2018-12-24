package entity;

import java.util.List;

/**
 * @author: 肖德子裕
 * @date: 2018/11/22 15:17
 * @description: 分页数据的封装
 */
public class PageResult <T>{
    private Long total;
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
