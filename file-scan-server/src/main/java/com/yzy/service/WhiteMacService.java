package com.yzy.service;


public interface WhiteMacService {
    /**
     * mac地址是否在mac地址白名单中
     * @param mac
     * @return
     */
    boolean isInWhiteMacList(String mac);
}
