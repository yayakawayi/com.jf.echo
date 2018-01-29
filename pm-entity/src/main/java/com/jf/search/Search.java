package com.jf.search;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：查询配置
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/5 11:27      陈飞(fly)                  新建
 * <p/>
 * *************************************************************************************************
 */
public class Search implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String DESC = "desc";

    private Collection<String> quickSearchProperties;

    private String quickSearchValue;

    /**
     * 筛选字段
     */
    private List<SearchFilter> filters;

    /**
     * 排序字段
     */
    private List<SearchOrder> sortOrders;

    /**
     * 分页信息
     */
    private PageInfo pageInfo;

    public Search() {
    }
    public Search(Collection<String> quickSearchProperties, String quickSearchValue) {
        this.quickSearchProperties = quickSearchProperties;
        this.quickSearchValue = quickSearchValue;
    }

    public Search(Collection<String> quickSearchProperties, String quickSearchValue, List<SearchFilter> filters, List<SearchOrder> sortOrders, PageInfo pageInfo) {
        this.quickSearchProperties = quickSearchProperties;
        this.quickSearchValue = quickSearchValue;
        this.filters = filters;
        this.sortOrders = sortOrders;
        this.pageInfo = pageInfo;
    }

    public Search(List<SearchFilter> filters, List<SearchOrder> sortOrders, PageInfo pageInfo) {
        this.filters = filters;
        this.sortOrders = sortOrders;
        this.pageInfo = pageInfo;
    }

    public Search(List<SearchFilter> filters) {
        this.filters = filters;
    }

    /**
     * 通过快速查询参数构造
     *
     * @param searchParam 快速查询参数
     */
    public Search(QuickSearchParam searchParam) {
        quickSearchValue = searchParam.getQuickSearchValue();
        quickSearchProperties = searchParam.getQuickSearchProperties();
        sortOrders = searchParam.getSortOrders();
        pageInfo = searchParam.getPageInfo();
    }

    /**
     * 通过高级查询参数构造
     *
     * @param searchParam 高级查询参数
     */
    public Search(SearchParam searchParam) {
        filters = searchParam.getFilters();
        sortOrders = searchParam.getSortOrders();
        pageInfo = searchParam.getPageInfo();
    }

    public Collection<String> getQuickSearchProperties() {
        return quickSearchProperties;
    }

    /**
     * 添加快速搜索属性名
     *
     * @param property 属性名
     * @return 返回已添加的快速搜索属性名集合
     */
    public Collection<String> addQuickSearchProperty(String property) {
        if (quickSearchProperties == null) {
            quickSearchProperties = new HashSet<String>();
        }
        if (StringUtils.isNotBlank(property)) {
            quickSearchProperties.add(property);
            this.setQuickSearchProperties(quickSearchProperties);
        }
        return quickSearchProperties;
    }

    public void setQuickSearchProperties(Collection<String> quickSearchProperties) {
        this.quickSearchProperties = quickSearchProperties;
    }

    public String getQuickSearchValue() {
        return quickSearchValue;
    }

    public void setQuickSearchValue(String quickSearchValue) {
        this.quickSearchValue = quickSearchValue;
    }

    public List<SearchFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<SearchFilter> filters) {
        this.filters = filters;
    }

    public void addFilter(SearchFilter searchFilter) {
        if (searchFilter != null) {
            if (filters == null) {
                filters = new ArrayList<SearchFilter>();
            }
            filters.add(searchFilter);
        }
    }

    public List<SearchOrder> getSortOrders() {
        return sortOrders;
    }

    public void setSortOrders(List<SearchOrder> sortOrders) {
        this.sortOrders = sortOrders;
    }

    public void addSortOrder(SearchOrder sortOrder) {
        if (sortOrder != null) {
            if (sortOrders == null) {
                sortOrders = new LinkedList<SearchOrder>();
            }
            sortOrders.add(sortOrder);
        }
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
