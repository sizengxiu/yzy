package com.yzy.service;

import com.yzy.model.FileScanDevice;
import com.yzy.model.ScanResult;

/**
 * @user szx
 * @date 2020/11/4 22:26
 */
public interface FileScanService {
    /**
     * 保存扫描结果
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/4 22:26
     */
     int saveScanResult(ScanResult result);
}
