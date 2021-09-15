package com.yzy.dao;

import com.yzy.model.YzzbDutyGroup;

import java.util.List;

public interface YzzbDutyGroupDao {
    int deleteByPrimaryKey(Integer id);

    int insert(YzzbDutyGroup record);

    int insertSelective(YzzbDutyGroup record);

    YzzbDutyGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YzzbDutyGroup record);

    int updateByPrimaryKey(YzzbDutyGroup record);

}