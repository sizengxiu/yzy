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

public class FileScanDeviceDO implements Serializable {
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
    private List<String> diskSerial;

    /**
     * mac地址
     */
    private List<String> macAddr;

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

    /**
     * 操作系统名字
     */
    private String os;
    /**
     * 扫描的文件总数
     */
    private Integer allFileCount;
    /**
     * 非法的文件数量
     */
    private Integer illegalFileCount;
    /**
     * 扫描耗时
     */
    private Double consumeTime;



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

    public List<String> getDiskSerial() {
        return diskSerial;
    }

    public void setDiskSerial(List<String> diskSerial) {
        this.diskSerial = diskSerial;
    }

    public List<String> getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(List<String> macAddr) {
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

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Integer getAllFileCount() {
        return allFileCount;
    }

    public void setAllFileCount(Integer allFileCount) {
        this.allFileCount = allFileCount;
    }

    public Integer getIllegalFileCount() {
        return illegalFileCount;
    }

    public void setIllegalFileCount(Integer illegalFileCount) {
        this.illegalFileCount = illegalFileCount;
    }

    public Double getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Double consumeTime) {
        this.consumeTime = consumeTime;
    }


    public FileScanDevice getPo(){
        FileScanDevice device=new FileScanDevice();
        device.setMd5(this.md5);
        device.setDataTime(this.dataTime);
        device.setAllFileCount(this.allFileCount);
        device.setCode(this.code);
        device.setConsumeTime(this.consumeTime);
        device.setIllegalFileCount(this.illegalFileCount);
        device.setOs(this.os);
        device.setResponsePerson(this.responsePerson);
        device.setSecretLevel(this.secretLevel);
        device.setUseScope(this.useScope);
        device.setDiskSerial(this.diskSerial.toString());
        device.setMacAddr(this.macAddr.toString());
        return device;
    }
}