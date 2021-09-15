package com.yzy.dao;

import com.yzy.model.User;
import com.yzy.model.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    /**
     * 获取当前最大的id
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/2 21:06
     */
    int getMaxUserId();
    /**
     * 删除所有用户
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/3 20:08
     */
    int deleteAllUser();

    /**
     * 批量添加用户
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/3 20:38
     */
    int addUserByBatch(@Param("list") List list);
    /**
     * 获取已经存在的员工编号列表
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/3 22:07
     */

    List<String> getExistCodeList(@Param("list") List list);

    /**
     * 获取用户列表
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/25 21:18
     */
    List<UserVo> getAllUsers();
}