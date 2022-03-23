package com.whm.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 15718
 */
@Controller
public class MainController {

    /**
     * 实现显示工作台首页
     * @return
     */
    @RequestMapping("/pages/workbench/main/index.do")
    public String index(){
        return "workbench/main/index";
    }
}
