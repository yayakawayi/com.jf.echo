package com.jf.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：表格分页返回数据
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/5 15:11      陈飞(fly)                  新建
 * <p/>
 * *************************************************************************************************
 */
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private int page = 1;
    /**
     * 总条数
     */
    private long records;
    /**
     * 总页数
     */
    private int total;
    /**
     * 当前页数据
     */
    private Collection rows;

    public PageResult() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Collection getRows() {
        return rows;
    }

    /**
     * 这里new的原因是Page.getContent()返回的是只读的List集合,
     * 而平台采用restful api client方式需要序列化和反序列化，返回的集合必须是可写的
     *
     * @see org.springframework.data.domain.Chunk#getContent()
     */
    public void setRows(List<T> list) {
        if (Objects.isNull(rows)) {
            rows = new ArrayList<T>();
        }
        rows.addAll(list);
    }

}
