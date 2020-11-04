package com.yzy.dao;

import com.yzy.model.FileScanLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileScanLogDao {
    int insert(FileScanLog record);

    int insertSelective(FileScanLog record);

    /**
     * 批量添加
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/4 23:08
     */
    int insertByBatch(@Param("list")List<FileScanLog> list);
}