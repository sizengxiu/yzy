package com.yzy.model;


import java.util.Date;
import java.util.List;

/**
 * @user szx
 * @date 2020/11/1 17:17
 */
public class IllegalFileInfo {
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
    //非法文件信息
    private List<String> files;

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

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
