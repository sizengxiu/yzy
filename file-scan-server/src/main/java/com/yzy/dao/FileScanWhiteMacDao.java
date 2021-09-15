package com.yzy.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileScanWhiteMacDao {
    /**
     * mac地址是否在mac地址白名单中
     * @param mac
     * @return
     */
    int isInWhiteMacList(@Param("mac")String mac);
}
