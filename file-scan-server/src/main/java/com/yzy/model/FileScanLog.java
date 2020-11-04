package com.yzy.model;

import java.io.Serializable;
import java.util.Date;

/**
 * FILE_SCAN_LOG
 * @author 
 */
public class FileScanLog implements Serializable {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * md5值
     */
    private String md5;

    /**
     * 扫描时间
     */
    private Date scanTime;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 包含的非法词语
     */
    private String illegalWord;

    private static final long serialVersionUID = 1L;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getScanTime() {
        return scanTime;
    }

    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getIllegalWord() {
        return illegalWord;
    }

    public void setIllegalWord(String illegalWord) {
        this.illegalWord = illegalWord;
    }
}