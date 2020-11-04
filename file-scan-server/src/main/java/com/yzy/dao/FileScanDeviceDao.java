package com.yzy.dao;

import com.yzy.model.FileScanDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileScanDeviceDao {
    int insert(FileScanDevice record);

    int insertSelective(FileScanDevice record);

    /**
     * 获取是否存在相同md5
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/4 23:01
     */
    int getMd5Count(@Param("md5") String md5);
}