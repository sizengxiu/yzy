package com.yzy.model;

import java.util.List;

/**
 * @user szx
 * @date 2020/11/4 21:47
 */
public class ScanResult {
    private FileScanDevice device;
    private List<FileScanLog> list;

    public FileScanDevice getDevice() {
        return device;
    }

    public void setDevice(FileScanDevice device) {
        this.device = device;
    }

    public List<FileScanLog> getList() {
        return list;
    }

    public void setList(List<FileScanLog> list) {
        this.list = list;
    }
}
