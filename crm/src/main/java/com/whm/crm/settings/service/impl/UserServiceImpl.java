package com.whm.crm.settings.service.impl;

import com.whm.crm.settings.domain.User;
import com.whm.crm.settings.mapper.UserMapper;
import com.whm.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 注册到ioc容器中
 * @author 15718
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    /**
     * 根据属性类型注入
     * Autowired注解的有一个required 其值默认为true表示注入bean的时候该bean必须存在，不然就会注入失败
     *
     * mapper对象是通过spring-mybatis中间件  将mapper注入到spring中的
     */
    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByLoginActAndPwd(Map<String, Object> map) {
        return userMapper.selectUserByLoginActAndPwd(map);
    }


}
