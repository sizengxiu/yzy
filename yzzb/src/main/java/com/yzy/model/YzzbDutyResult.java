package com.yzy.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 排班结果
 * @user szx
 * @date 2021/6/6 15:56
 */
public class YzzbDutyResult {
    private DayDutyResultVo monday;
    private DayDutyResultVo tuesday;
    private DayDutyResultVo wednesday;
    private DayDutyResultVo thursday;
    private DayDutyResultVo friday;
    private DayDutyResultVo saturday;
    private DayDutyResultVo sunday;



    public DayDutyResultVo getMonday() {
        return monday;
    }

    public void setMonday(DayDutyResultVo monday) {
        this.monday = monday;
    }

    public DayDutyResultVo getTuesday() {
        return tuesday;
    }

    public void setTuesday(DayDutyResultVo tuesday) {
        this.tuesday = tuesday;
    }

    public DayDutyResultVo getWednesday() {
        return wednesday;
    }

    public void setWednesday(DayDutyResultVo wednesday) {
        this.wednesday = wednesday;
    }

    public DayDutyResultVo getThursday() {
        return thursday;
    }

    public void setThursday(DayDutyResultVo thursday) {
        this.thursday = thursday;
    }

    public DayDutyResultVo getFriday() {
        return friday;
    }

    public void setFriday(DayDutyResultVo friday) {
        this.friday = friday;
    }

    public DayDutyResultVo getSaturday() {
        return saturday;
    }

    public void setSaturday(DayDutyResultVo saturday) {
        this.saturday = saturday;
    }

    public DayDutyResultVo getSunday() {
        return sunday;
    }

    public void setSunday(DayDutyResultVo sunday) {
        this.sunday = sunday;
    }

    @Override
    public String toString() {
        return "YzzbDutyResult{" +
                "monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                '}';
    }
}

