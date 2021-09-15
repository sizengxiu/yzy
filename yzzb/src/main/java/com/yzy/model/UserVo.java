package com.yzy.model;

/**
 * @user szx
 * @date 2021/6/29 22:30
 */
public class UserVo extends User {
    private int userState;
    private int attendDuty;

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public int getAttendDuty() {
        return attendDuty;
    }

    public void setAttendDuty(int attendDuty) {
        this.attendDuty = attendDuty;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "userState=" + userState +
                ", attendDuty=" + attendDuty +
                '}';
    }
}
