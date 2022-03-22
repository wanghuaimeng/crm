package com.whm.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * RequestMapping默认使用value属性值
 * name属性的
 * @author 15718
 */
@Controller
@RequestMapping("/pages/workbench/")
public class WorkbenchIndexController {

    @RequestMapping("index.do")
    public String index(){
        return "workbench/index";
    }
}
