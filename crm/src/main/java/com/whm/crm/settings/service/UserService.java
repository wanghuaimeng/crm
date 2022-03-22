package com.whm.crm.settings.service;

import com.whm.crm.settings.domain.User;

import java.util.Map;

/**
 * @author 15718
 */
public interface UserService {

    /**
     * 调用mapper的层的selectUserByLoginActAndPwd 根据LoginAct loginPwd查询User
     * @param map
     * @return
     */
    public User queryUserByLoginActAndPwd(Map<String, Object> map);
}
