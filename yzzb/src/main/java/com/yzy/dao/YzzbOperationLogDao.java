package com.yzy.dao;

import com.yzy.model.YzzbOperationLog;

public interface YzzbOperationLogDao {
    int deleteByPrimaryKey(Integer id);

    int insert(YzzbOperationLog record);

    int insertSelective(YzzbOperationLog record);

    YzzbOperationLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YzzbOperationLog record);

    int updateByPrimaryKey(YzzbOperationLog record);
}