package com.yzy.model;

import java.util.Date;

/**
 * @user szx
 * @date 2021/6/28 21:42
 */
public class YzzbDutyZbVo {

    /**
     * 值班日期
     */
    private Date date;

    /**
     * 发布状态
     */
    private Integer publishState;


    private Integer weekIndex;

    private String name;
    private String phone;
    private int sex;



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

    public Integer getWeekIndex() {
        return weekIndex;
    }

    public void setWeekIndex(Integer weekIndex) {
        this.weekIndex = weekIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "YzzbDutyZbVo{" +
                "date=" + date +
                ", publishState=" + publishState +
                ", weekIndex=" + weekIndex +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                '}';
    }
}
