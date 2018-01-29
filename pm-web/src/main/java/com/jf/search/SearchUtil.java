package com.jf.search;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/5 14:58      陈飞(fly)                  新建
 * <p/>
 * *************************************************************************************************
 */
public final class SearchUtil {

    /**
     * 获取查询配置
     *
     * @param request
     * @return
     */
    public static Search genSearch(ServletRequest request){
        List<SearchFilter> filters = getFilters(request);
        List<SearchOrder> sortOrders = getSorts(request);
        PageInfo info = getPageInfo(request);

        Search search = new Search(filters, sortOrders, info);
        String quickSearchValue = request.getParameter("Quick_value");
        String quick_property_string = request.getParameter("Quick_property");
        if (StringUtils.isNotBlank(quick_property_string)){
            String[] split  = quick_property_string.split(",");
            for (String s :split){search.addQuickSearchProperty(s);}
        }
        if (StringUtils.isNotBlank(quickSearchValue)){
            search.setQuickSearchValue(quickSearchValue);
        }
        return search;
    }

    /**
     * 根据查询参数构造查询字段Map
     *
     * @param searchParams
     * @return 查询字段map
     */
    public static List<SearchFilter> getFilters(Map<String, Object> searchParams) {
        List<SearchFilter> filters = new ArrayList<SearchFilter>();

        SearchFilter filter;
        for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
            String key = entry.getKey();
            String fieldType = "String";
            if (key.contains("__")) {
                String[] tmps = key.split("__");
                key = tmps[0];
                fieldType = tmps[1];
            }
            Object value = entry.getValue();
            ;
            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException("查询参数" + key + ",格式有误");
            }
            String filedName = names[1];
            SearchFilter.Operator operator = SearchFilter.Operator.valueOf(names[0]);

            // 创建searchFilter
            filter = new SearchFilter(filedName, value, operator, fieldType);
            filters.add(filter);
        }

        return filters;
    }

    /**
     * 根据Request请求构造查询字段Map
     *
     * @param request 查询参数示例：Q_EQ_name
     * @return
     */
    public static List<SearchFilter> getFilters(ServletRequest request) {
        Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "Q_");
        return getFilters(searchParams);
    }

    /**
     * 获取排序字段
     *
     * @param request
     * @return
     */
    public static List<SearchOrder> getSorts(ServletRequest request) {
        List<SearchOrder> sortOrders = new ArrayList<SearchOrder>();
        //获取表格排序字段
        String sord = request.getParameter("sord");
        String sidx = request.getParameter("sidx");
        if (StringUtils.isNotBlank(sidx)) {
            if (StringUtils.isNotBlank(sord)) {
                sortOrders.add(new SearchOrder(sidx, "desc".equalsIgnoreCase(sord) ? SearchOrder.Direction.DESC : SearchOrder.Direction.ASC));
            } else {
                //默认降序排列
                sortOrders.add(new SearchOrder(sidx, SearchOrder.Direction.DESC));
            }
        }
        //获取参数中的排序字段,S_name
        Map<String, Object> sortParams = WebUtils.getParametersStartingWith(request, "S_");
        for (String key : sortParams.keySet()) {
            sortOrders.add(new SearchOrder(key, "desc".equalsIgnoreCase(sortParams.get(key).toString()) ? SearchOrder.Direction.DESC : SearchOrder.Direction.ASC));
        }
        if (sortOrders.isEmpty()) {
            return null;
        }
        return sortOrders;
    }

    /**
     * 获取表格分页信息
     *
     * @param request
     * @return
     */
    public static PageInfo getPageInfo(ServletRequest request) {
        PageInfo info = new PageInfo();
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if (StringUtils.isNotBlank(page)) {
            info.setPage(Integer.parseInt(page));
        }
        if (StringUtils.isNotBlank(rows)) {
            info.setRows(Integer.parseInt(rows));
        }
        return info;
    }

    public static void main(String[] args) {
    }

}
