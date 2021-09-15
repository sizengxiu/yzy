package com.yzy.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackIpListDao {
    /**
     * 保存黑户访问记录
     * @param ip
     * @param mac
     * @return
     */
    int saveBlackVisitRecord(@Param("ip")String ip,@Param("mac")String mac);
}
