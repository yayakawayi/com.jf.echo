package com.jf.controller;

import com.jf.SiteService;
import com.jf.constant.EnumConstant;
import com.jf.entity.Site;
import com.jf.json.OperateStatus;
import com.jf.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/site")
public class SiteController {
    @Autowired
    protected SiteService siteService;

    /**
     * 抓取天气网站管理页
     */
    @RequestMapping("/showSite")
    public String showSite() {
        return "site/SiteView";
    }

    /**
     * 显示所有网站信息
     */
    @ResponseBody
    @RequestMapping("/list")
    public OperateStatus findAll(HttpServletRequest request) {
        OperateStatus status = OperateStatus.defaultSuccess();
        Search search = SearchUtil.genSearch(request);
        search.addSortOrder(SearchOrder.asc("sort"));
        PageResult<Site> page = siteService.findPage(search);
        status.setData(page);
        return status;
    }

    /**
     * 设置网站的顺序和状态
     */
    @ResponseBody
    @RequestMapping("/setSites")
    public OperateStatus setSites(String siteSorts, String siteStatus) {
        OperateStatus status = OperateStatus.defaultSuccess();
        try {
            String[] sorts = siteSorts.split(",");
            String[] status1 = siteStatus.split(",");
            for (int i = 0; i < sorts.length; i++) {
                Site site = siteService.findUniqueBy("name", sorts[i]);
                site.setSort(i);
                site.setStatus(EnumConstant.ServerStatus.valueOf(status1[i]));
                siteService.save(site);
            }
        } catch (Exception e) {
            status.setSuccess(false);
            return status;
        }
        return status;
    }

}
