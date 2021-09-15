package com.yzy.model;

/**
 * 星期分组用户数
 * @user szx
 * @date 2021/6/2 20:36
 */
public class WeekGroupCount {
    private int mondayCount;
    private int tuesdayCount;
    private int wednesdayCount;
    private int thursdayCount;
    private int fridayCount;
    private int saturdayCount;
    private int sundayCount;

    public int getMondayCount() {
        return mondayCount;
    }

    public void setMondayCount(int mondayCount) {
        this.mondayCount = mondayCount;
    }

    public int getTuesdayCount() {
        return tuesdayCount;
    }

    public void setTuesdayCount(int tuesdayCount) {
        this.tuesdayCount = tuesdayCount;
    }

    public int getWednesdayCount() {
        return wednesdayCount;
    }

    public void setWednesdayCount(int wednesdayCount) {
        this.wednesdayCount = wednesdayCount;
    }

    public int getThursdayCount() {
        return thursdayCount;
    }

    public void setThursdayCount(int thursdayCount) {
        this.thursdayCount = thursdayCount;
    }

    public int getFridayCount() {
        return fridayCount;
    }

    public void setFridayCount(int fridayCount) {
        this.fridayCount = fridayCount;
    }

    public int getSaturdayCount() {
        return saturdayCount;
    }

    public void setSaturdayCount(int saturdayCount) {
        this.saturdayCount = saturdayCount;
    }

    public int getSundayCount() {
        return sundayCount;
    }

    public void setSundayCount(int sundayCount) {
        this.sundayCount = sundayCount;
    }

    @Override
    public String toString() {
        return "WeekGroupCount{" +
                "mondayCount=" + mondayCount +
                ", tuesdayCount=" + tuesdayCount +
                ", wednesdayCount=" + wednesdayCount +
                ", thursdayCount=" + thursdayCount +
                ", fridayCount=" + fridayCount +
                ", saturdayCount=" + saturdayCount +
                ", sundayCount=" + sundayCount +
                '}';
    }
}
