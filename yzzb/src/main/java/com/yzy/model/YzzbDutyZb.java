package com.yzy.model;

import java.io.Serializable;
import java.util.Date;

/**
 * yzzb_duty_zb
 * @author 
 */
public class YzzbDutyZb implements Serializable,Cloneable {
    private Integer id;
    private Integer userId;

    /**
     * 值班日期
     */
    private Date date;

    /**
     * 发布状态
     */
    private Integer publishState;

    /**
     * 数据状态0/1
     */
    private Integer state;

    private Date insertTime;

    private Date updateTime;

    private Integer weekIndex;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPublishState() {
        return publishState;
    }

    public void setPublishState(Integer publishState) {
        this.publishState = publishState;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getWeekIndex() {
        return weekIndex;
    }

    public void setWeekIndex(Integer weekIndex) {
        this.weekIndex = weekIndex;
    }

    @Override
    public YzzbDutyZb clone(){
        YzzbDutyZb zb=null;
        try {
            zb = (YzzbDutyZb)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return zb;
    }
}