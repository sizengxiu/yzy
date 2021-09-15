package com.yzy.model;

/**
 * @user szx
 * @date 2021/6/30 21:55
 */
public class UserCountVo {

    private int weekIndex;//周几
    private int userCount;//人数

    public int getWeekIndex() {
        return weekIndex;
    }

    public void setWeekIndex(int weekIndex) {
        this.weekIndex = weekIndex;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    @Override
    public String toString() {
        return "UserCountVo{" +
                "weekIndex=" + weekIndex +
                ", userCount=" + userCount +
                '}';
    }
}
