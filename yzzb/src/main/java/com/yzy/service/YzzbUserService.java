package com.yzy.service;

import com.yzy.model.User;
import com.yzy.model.UserVo;

import java.util.List;

/**
 * 值班用户服务
 * @user szx
 * @date 2021/5/19 20:33
 */
public interface YzzbUserService {

    /**
     * 批量添加用户
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/2 20:20
     */
    int addUserByBatch(List<User> list);
    /**
     * 获取用户列表
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/25 21:17
     */
    List<UserVo> getAllUsers();
}
