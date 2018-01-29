package com.jf.controller.base;

import com.jf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/base")
public class BaseController {

    @Autowired
    protected UserService userService;
    @Autowired
    protected ProjectService projectService;
    @Autowired
    protected ServerService serverService;
    @Autowired
    protected WebService webService;
    @Autowired
    protected SiteService siteService;

}
