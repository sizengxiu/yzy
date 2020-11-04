package com.yzy.model;

import java.util.List;

/**
 * @user szx
 * @date 2020/11/4 22:38
 */
public class ScanResult {
    private IllegalFileInfo device;
    private List<ScanLog> list;

    public IllegalFileInfo getDevice() {
        return device;
    }

    public void setDevice(IllegalFileInfo device) {
        this.device = device;
    }

    public List<ScanLog> getList() {
        return list;
    }

    public void setList(List<ScanLog> list) {
        this.list = list;
    }
}
