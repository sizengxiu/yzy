package com.yzy.model;

/**
 * @user szx
 * @date 2021/6/6 16:31
 */
public class DayDutyResultVo implements Cloneable {
    //几号
    private int day;

    private String employeeAName;

    private String employeeAphone;

    private String employeeBName;
    private String employeeBphone;

    private String employeeCName;
    private String employeeCphone;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getEmployeeAName() {
        return employeeAName;
    }

    public void setEmployeeAName(String employeeAName) {
        this.employeeAName = employeeAName;
    }

    public String getEmployeeAphone() {
        return employeeAphone;
    }

    public void setEmployeeAphone(String employeeAphone) {
        this.employeeAphone = employeeAphone;
    }

    public String getEmployeeBName() {
        return employeeBName;
    }

    public void setEmployeeBName(String employeeBName) {
        this.employeeBName = employeeBName;
    }

    public String getEmployeeBphone() {
        return employeeBphone;
    }

    public void setEmployeeBphone(String employeeBphone) {
        this.employeeBphone = employeeBphone;
    }

    public String getEmployeeCName() {
        return employeeCName;
    }

    public void setEmployeeCName(String employeeCName) {
        this.employeeCName = employeeCName;
    }

    public String getEmployeeCphone() {
        return employeeCphone;
    }

    public void setEmployeeCphone(String employeeCphone) {
        this.employeeCphone = employeeCphone;
    }

    @Override
    public String toString() {
        return "DayDutyResult{" +
                "day=" + day +
                ", employeeAName='" + employeeAName + '\'' +
                ", employeeAphone='" + employeeAphone + '\'' +
                ", employeeBName='" + employeeBName + '\'' +
                ", employeeBphone='" + employeeBphone + '\'' +
                ", employeeCName='" + employeeCName + '\'' +
                ", employeeCphone='" + employeeCphone + '\'' +
                '}';
    }

    @Override
    public DayDutyResultVo clone(){
        DayDutyResultVo dayDutyResultVo=null;
        try {
            dayDutyResultVo=(DayDutyResultVo)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return dayDutyResultVo;
    }


}
