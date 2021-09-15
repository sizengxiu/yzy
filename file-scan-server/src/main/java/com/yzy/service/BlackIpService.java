package com.yzy.service;

public interface BlackIpService {
    /**
     * 保存黑户访问记录
     * @param ip
     * @return
     */
     int saveBlackVisitRecord(String ip,String mac);
}
