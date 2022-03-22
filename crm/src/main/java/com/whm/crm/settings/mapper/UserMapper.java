package com.whm.crm.settings.mapper;

import com.whm.crm.settings.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author 15718
 */
public interface UserMapper {

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") String id);


    /**
     * 插入一个用户记录
     * @param record
     * @return
     */
    int insert(User record);

    /**
     *选择性插入  但是主键字段为空会插入失败
     * @param record
     * @return
     */
    int insertSelective(User record);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    User selectByPrimaryKey(@Param("id")String id);

    /**
     * 根据主键选择性更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);

    /**
     * map中保存着 loginAct 和 pwd
     * 根据 loginAct 和 pwd  查询 并返回
     * @param map
     * @return
     */
    User selectUserByLoginActAndPwd(Map<String, Object> map);

}