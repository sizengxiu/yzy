package com.yzy.dao;

import com.yzy.model.YzzbDutyGroupUser;

import java.util.List;

public interface YzzbDutyGroupUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(YzzbDutyGroupUser record);

    int insertSelective(YzzbDutyGroupUser record);

    YzzbDutyGroupUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YzzbDutyGroupUser record);

    int updateByPrimaryKey(YzzbDutyGroupUser record);
    /**
     * 获取用户分组信息
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/6 16:12
     */
    List<YzzbDutyGroupUser> getUserGroupList();
}