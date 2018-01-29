package com.jf.base;

import com.jf.dao.base.BaseDao;
import com.jf.entity.base.BaseEntity;
import com.jf.entity.base.SimpleEntity;
import com.jf.search.PageResult;
import com.jf.search.Search;
import com.jf.search.SearchFilter;
import com.jf.exception.CoreException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/7/5 14:44      陈飞(fly)                    新建
 * <br>
 * *************************************************************************************************<br>
 */
public abstract class BaseService<T extends SimpleEntity> {

    abstract protected BaseDao<T> getDao();

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public T save(T entity) throws CoreException {
            Date nowDate = new Date();
        if(entity instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity)entity;
            if (StringUtils.isNotEmpty(entity.getId())) {// 更新：以当前时间作为更新时间
                BaseEntity b = (BaseEntity)getDao().findOne(entity.getId());
                baseEntity.setCreateTime(b.getCreateTime());
                baseEntity.setLastUpdateTime(nowDate);
            } else {// 保存：以当前时间作为创建时间和更新时间
                baseEntity.setCreateTime(nowDate);
                baseEntity.setLastUpdateTime(nowDate);
            }
        }
        return getDao().save(entity);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void save(List<T> entityList) throws CoreException {
        Date nowDate = new Date();
        for (T entity : entityList) {
            if(entity instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) entity;
                if (entity.getId() == null) {// 更新：以当前时间作为更新时间
                    baseEntity.setLastUpdateTime(nowDate);
                } else {// 保存：以当前时间作为创建时间和更新时间
                    baseEntity.setCreateTime(nowDate);
                    baseEntity.setLastUpdateTime(nowDate);
                }
            }
        }
        getDao().save(entityList);
    }

    /**
     * 基于主键集合查询集合数据对象
     */
    @Transactional(readOnly = true)
    public List<T> findAll() {
        List<T> list = new ArrayList<T>();
        for (T entity : this.getDao().findAll()) {
            list.add(entity);
        }
        return list;
    }

    /**
     * 基于主键查询单一数据对象
     */
    @Transactional(readOnly = true)
    public T getById(String id) {
        Validation.notNull(id, "未找到指定对象");
        return getDao().findOne(id);
    }

    /**
     * 基于主键集合查询集合数据对象
     *
     * @param ids 主键集合
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("rawtypes")
    public List<T> findByIds(final Collection<String> ids) {
        Validation.isTrue(ids != null, "必须提供有效查询对象Id集合");
        if (ids.size() > 0) {
            SearchFilter filter = new SearchFilter("id", ids, SearchFilter.Operator.IN);
            return findByFilter(filter);
        } else {
            return null;
        }
    }

    /**
     * 主键删除
     *
     * @param id 主键
     * @return 返回操作结果对象
     */
//    public void delete(final String id) {
//        T entity = getById(id);
//        if (entity != null) {
//            getDao().delete(entity);
//        }
//    }

    /**
     * 批量数据删除操作 其实现只是简单循环集合每个元素调用
     * 因此并无实际的Batch批量处理，如果需要数据库底层批量支持请自行实现
     *
     * @param ids 待批量操作数据集合
     */
    public void delete(Collection<String> ids) {
        List<T> list = findByIds(ids);
        if (list != null && list.size() > 0) {
            getDao().delete(list);
        }
    }
    //批量删除
    public void delete(String ids) {
        String[] split = ids.split(",");
        List<String> idsList = new ArrayList<>();
        for (String s:split) {
            idsList.add(s);
        }
        List<T> list = findByIds(idsList);
        if (list != null && list.size() > 0) {
            getDao().delete(list);
        }
    }
    /**
     * 根据泛型对象属性和值查询集合对象
     *
     * @param property 属性名，即对象中数量变量名称
     * @param value    参数值
     */
    @Transactional(readOnly = true)
    public List<T> findBy(final String property, final Object value) {
        Specification<T> spec = (root, query, builder) -> {
            Path expression = root.get(property);
            return builder.equal(expression, value);
        };
        return getDao().findAll(spec);
    }

    /**
     * 根据泛型对象属性和值查询唯一对象
     * @param property 属性名，即对象中数量变量名称
     * @param value    参数值
     * @return 未查询到返回null，如果查询到多条数据则抛出异常
     */
    @Transactional(readOnly = true)
    public T findUniqueBy(final String property, final Object value) {
        Specification<T> spec = (root, query, builder) -> {
            Path expression = root.get(property);
            return builder.equal(expression, value);
        };
        return getDao().findOne(spec);
    }

    /**
     * 单一条件对象查询数据集合
     */
    @Transactional(readOnly = true)
    public List<T> findByFilter(SearchFilter searchFilter) {
        return getDao().findByFilter(searchFilter);
    }

    /**
     * 单一条件对象查询唯一数据
     */
    @Transactional(readOnly = true)
    public T findUniqueByFilter(SearchFilter searchFilter) {
        return getDao().findUniqueByFilter(searchFilter);
    }

    /**
     * 基于查询条件count记录数据
     */
    @Transactional(readOnly = true)
    public long count(Search searchConfig) {
        return getDao().count(searchConfig);
    }

    /**
     * 基于动态组合条件对象和排序定义查询数据集合
     */
    @Transactional(readOnly = true)
    public List<T> findByFilters(Search searchConfig) {
        return getDao().findByFilters(searchConfig);
    }

    /**
     * 基于动态组合条件对象和分页(含排序)对象查询数据集合
     */
    @Transactional(readOnly = true)
    public PageResult<T> findPage(Search searchConfig) {
        return getDao().findPage(searchConfig);
    }

    /**
     * 排序查找
     * @param sort
     * @return
     */
    public List<T> findAll(Sort sort){
        return getDao().findAll(sort);
    }
}
