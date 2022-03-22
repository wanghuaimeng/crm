package com.whm.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 15718
 */
@Controller
public class IndexController {

    /**
     * 权限修饰符号使用public 是因为public允许同一个工程访问
     * @return
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
