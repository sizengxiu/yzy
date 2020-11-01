package com.yzy.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @user szx
 * @date 2020/10/31 20:57
 */

public class DeviceInfo {
    //责任人
    private String responsePerson;
    //资产编号
    private String code;
    //使用范围
    private int useScope;
    //密级
    private int secretLevel;
    //硬盘序列号
    private String diskSerial;
    //mac地址
    private String mac;
    //扫描时间
    private Date scanTime;

    public String getResponsePerson() {
        return responsePerson;
    }

    public void setResponsePerson(String responsePerson) {
        this.responsePerson = responsePerson;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getUseScope() {
        return useScope;
    }

    public void setUseScope(int useScope) {
        this.useScope = useScope;
    }

    public int getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel(int secretLevel) {
        this.secretLevel = secretLevel;
    }

    public String getDiskSerial() {
        return diskSerial;
    }

    public void setDiskSerial(String diskSerial) {
        this.diskSerial = diskSerial;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Date getScanTime() {
        return scanTime;
    }

    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "responsePerson='" + responsePerson + '\'' +
                ", code='" + code + '\'' +
                ", useScope=" + useScope +
                ", secretLevel=" + secretLevel +
                ", diskSerial='" + diskSerial + '\'' +
                ", mac='" + mac + '\'' +
                ", scanTime=" + scanTime +
                '}';
    }
}
