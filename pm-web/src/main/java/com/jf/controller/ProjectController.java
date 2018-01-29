package com.jf.controller;

import com.jf.ProjectService;
import com.jf.controller.base.BaseController;
import com.jf.entity.Project;
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
import java.util.Map;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {


    /**
     * 得到所有的项目信息
     */
    @ResponseBody
    @RequestMapping("combo")
    public OperateStatus combo() {
        OperateStatus status = OperateStatus.defaultSuccess();
        status.setData(projectService.findAll());
        return status;
    }

    /**
     * 显示项目页
     */
    @RequestMapping("/showProject")
    public String show() {
        return "project/ProjectView";
    }

    /**
     * 基于动态组合条件对象和分页(含排序)对象查询数据集合
     */
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Project> list(HttpServletRequest request) {
        Search search = SearchUtil.genSearch(request);
        return projectService.findPage(search);
    }

    /**
     * 保存
     *
     * @param project
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public OperateStatus save(Project project) {
        OperateStatus status = OperateStatus.defaultSuccess();
        projectService.save(project);
        return status;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public OperateStatus delete(String ids) {
        OperateStatus status = OperateStatus.defaultSuccess();
        projectService.delete(ids);
        return status;
    }
}
