package com.yzy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * FILE_SCAN_DEVICE
 * @author 
 */

public class FileScanDevice implements Serializable {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 责任人
     */
    private String responsePerson;

    /**
     * 使用范围
     */
    private Short useScope;

    /**
     * 密级
     */
    private Short secretLevel;

    /**
     * 硬盘序列号
     */
    private String diskSerial;

    /**
     * mac地址
     */
    private String macAddr;

    /**
     * md5获取唯一性
     */
    private String md5;

    /**
     * 数据时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataTime;

    private String code;



    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResponsePerson() {
        return responsePerson;
    }

    public void setResponsePerson(String responsePerson) {
        this.responsePerson = responsePerson;
    }

    public Short getUseScope() {
        return useScope;
    }

    public void setUseScope(Short useScope) {
        this.useScope = useScope;
    }

    public Short getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel(Short secretLevel) {
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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}