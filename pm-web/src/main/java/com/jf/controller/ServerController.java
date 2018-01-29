package com.jf.controller;
import com.jf.controller.base.BaseController;
import com.jf.json.OperateStatus;
import com.jf.search.PageResult;
import com.jf.search.Search;
import com.jf.search.SearchUtil;
import com.jf.entity.Server;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/server")
public class ServerController extends BaseController {


    /**
     * 显示服务页
     */
    @RequestMapping("/showServer")
    public String show() {
        return "server/ServerView";
    }

    /**
     * 基于动态组合条件对象和分页(含排序)对象查询数据集合
     */
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Server> list(HttpServletRequest request) {
        Search search = SearchUtil.genSearch(request);
        return serverService.findPage(search);
    }

    /**
     * 保存
     *
     * @param server
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public OperateStatus save(Server server) {
        OperateStatus status = OperateStatus.defaultSuccess();
        serverService.save(server);
        return status;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public OperateStatus delete(String ids) {
        OperateStatus status = OperateStatus.defaultSuccess();
        serverService.delete(ids);
        return status;
    }


}
