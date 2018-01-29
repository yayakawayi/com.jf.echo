package com.jf.dao.base;

import com.jf.entity.base.SimpleEntity;
import com.jf.search.PageResult;
import com.jf.search.Search;
import com.jf.search.SearchFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseDao<T extends SimpleEntity> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {

    /**
     * 获取业务实体类型
     *
     * @return 业务实体类型
     */
    Class<T> getEntityClass();

    /**
     * 根据业务对象属性的值判断业务实体是否存在
     *
     * @param property 属性名，即对象中数量变量名称
     * @param value    参数值
     * @return 未查询到返回false，如果查询到一条或多条数据则返回true
     */
    boolean isExists(String property, Object value);

    /**
     * 单一条件对象查询数据集合
     */
    List<T> findByFilter(SearchFilter searchFilter);

    /**
     * 单一条件对象查询唯一数据
     */
    public T findUniqueByFilter(SearchFilter searchFilter);

    /**
     * 基于查询条件count记录数据
     */
    long count(Search searchConfig);

    /**
     * 基于动态组合条件对象和排序定义查询数据集合
     */
    List<T> findByFilters(Search searchConfig);

    /**
     * 基于动态组合条件对象和分页(含排序)对象查询数据集合
     */
    PageResult<T> findPage(Search searchConfig);

}
