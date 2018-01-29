package com.jf.controller;

import com.jf.controller.base.BaseController;
import com.jf.entity.User;
import com.jf.json.OperateStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @RequestMapping("/showUser")
    public String showUser(HttpServletRequest request, Model model) throws Exception {
        List<User> allUsers = userService.findAll();
        model.addAttribute("allUsers", allUsers);
        return "user/userListView";
    }

    @RequestMapping("/msg")
    public String msg() {
        return "msg/msg";
    }

    /**
     * 保存
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public OperateStatus save(User user) {
        OperateStatus status = OperateStatus.defaultSuccess();
        userService.save(user);
        return status;
    }
}
