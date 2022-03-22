package com.whm.crm.settings.web.controller;

import com.whm.crm.commons.contants.Contants;
import com.whm.crm.commons.domain.ReturnObject;
import com.whm.crm.commons.utils.DateUtils;
import com.whm.crm.settings.domain.User;
import com.whm.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 15718
 */
@Controller
@RequestMapping("/pages/settings/qx/user/")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页
     * @return String
     */
    @RequestMapping("toLogin.do")
    public String toLogin(){
        //这个路径  要根据视图解析器的前缀 后缀书写
        return "settings/qx/user/login";
    }

    /**
     * 实现登录功能
     * (@ResponseBody)用于标识一个控制器方法，可以将该方法的返回值直接作为响应报文的响应体响应到浏览器
     * @return Object
     */
    @RequestMapping("login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd,HttpServletRequest request, HttpServletResponse response){



        //将参数封装进map中 供service层使用
        Map<String, Object> map = new HashMap<>(2);
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);

        //查询
        User user = userService.queryUserByLoginActAndPwd(map);

        //用于封装返回值信息的  会被转换成json
        ReturnObject returnObject = new ReturnObject();

        //根据返回的user进行判断  是否能登录成功
        if(user == null){
            //用户名或者密码错误 未查询到user对象
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或者密码错误");
        }else{
            //获取过期时间
            String expireTime = user.getExpireTime();
            //获取当前时间的指定字符串格式    使用自己封装的utils工具类
            String currentTime = DateUtils.formateDateTime(new Date(), "yyyy-MM-dd hh:mm:ss");
            if(currentTime.compareTo(expireTime) > 0){
                //当前时间大于过期时间
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号已过期");
            }else if(Contants.RETURN_OBJECT_CODE_FAIL.equals(user.getLockState())){
                //此时状态被锁定
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号已被锁定");
            }else if(!(user.getAllowIps().contains(request.getRemoteAddr()))){
                //通过getRemoteAddr获取客户端或最后一层代理的ip
                //ip 受限 不允许登录
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("当前ip不安全");
            }
            else{
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);

                HttpSession session = request.getSession();
                //将查询的user保存到session中 用于登录之后的相关信息的回显
                session.setAttribute(Contants.SESSION_USER, user);

                //将账号密码保存到cookie中
                Cookie cLoginAct = new Cookie("loginAct", user.getLoginAct());
                Cookie cLoginPwd = new Cookie("loginPwd", user.getLoginPwd());
                if("true".equals(isRemPwd)){
                    //设置最大生存时间是10天
                    cLoginAct.setMaxAge(864000);
                    cLoginPwd.setMaxAge(864000);
                }else{
                    //当前台未选择保存  则设置生存时间为0  这样会导致同名的cookie被删除
                    cLoginAct.setMaxAge(0);
                    cLoginPwd.setMaxAge(0);
                }
                //设置完时间再将指定的 cookie 添加到响应中
                response.addCookie(cLoginAct);
                response.addCookie(cLoginPwd);
            }
        }
        return returnObject;
    }

    /**
     * 实现安全退出
     * 通过/index进行跳转到登录页
     * @return
     */
    @RequestMapping("logout.do")
    public String loggingOut(HttpServletResponse response, HttpSession session){
        //删除cookie
        Cookie cLoginAct = new Cookie("loginAct", "");
        Cookie cLoginPwd = new Cookie("loginPwd", "");
        cLoginAct.setMaxAge(0);
        cLoginPwd.setMaxAge(0);
        //防止出现两个同名的cookie   只有name、path、domain完全一样 客户端才会视为同一个cookie
        cLoginAct.setDomain("localhost");
        cLoginPwd.setDomain("localhost");
        cLoginAct.setPath("/crm/pages/settings/qx/user");
        cLoginPwd.setPath("/crm/pages/settings/qx/user");
        //设置完时间再将指定的 cookie 添加到响应中
        response.addCookie(cLoginAct);
        response.addCookie(cLoginPwd);
        //使session无效
        session.invalidate();
        return "index";
    }
}
