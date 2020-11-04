package com.yzy.model;

import com.alibaba.fastjson.annotation.JSONField;

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
    private String macAddr;
    //扫描时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date dataTime;


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

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }


}
