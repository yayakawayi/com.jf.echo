package com.jf.controller;

import com.jf.WebService;
import com.jf.controller.base.BaseController;
import com.jf.entity.Web;
import com.jf.json.OperateStatus;
import com.jf.search.PageResult;
import com.jf.search.Search;
import com.jf.search.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/web")
public class WebController extends BaseController {



    /**
     * 显示视图页
     */
    @RequestMapping("/showWeb")
    public String show() {
        return "web/WebView";
    }

    /**
     * 基于动态组合条件对象和分页(含排序)对象查询数据集合
     */
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Web> list(HttpServletRequest request) {
        Search search = SearchUtil.genSearch(request);
        return webService.findPage(search);
    }

    /**
     * 保存
     *
     * @param web
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public OperateStatus save(Web web) {
        OperateStatus status = OperateStatus.defaultSuccess();
        webService.save(web);
        return status;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public OperateStatus delete(String ids) {
        OperateStatus status = OperateStatus.defaultSuccess();
        webService.delete(ids);
        return status;
    }
}
