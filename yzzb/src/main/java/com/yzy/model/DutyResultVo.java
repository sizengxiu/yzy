package com.yzy.model;

import java.util.Date;

/**
 * 返回给前端的排班结果
 * @user szx
 * @date 2021/6/25 16:52
 */
public class DutyResultVo  implements  Cloneable{

    private int userId;
    private String code;
    private int day;
    private int weekIndex;
    private String name;
    private String phone;
    private String fixedPhone;
    private int sex;
    private Date date;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getWeekIndex() {
        return weekIndex;
    }

    public void setWeekIndex(int weekIndex) {
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getFixedPhone() {
        return fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }

    @Override
    public DutyResultVo clone(){
        DutyResultVo dutyResultVo=null;
        try {
            dutyResultVo=(DutyResultVo)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return dutyResultVo;
    }

    @Override
    public String toString() {
        return "DutyResultVo{" +
                "userId=" + userId +
                ", day=" + day +
                ", weekIndex=" + weekIndex +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", date=" + date +
                '}';
    }
}
