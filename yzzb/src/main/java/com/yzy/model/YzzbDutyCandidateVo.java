package com.yzy.model;

/**
 * @user szx
 * @date 2021/6/15 21:39
 */
public class YzzbDutyCandidateVo extends YzzbDutyCandidate {

    //分组的id
    private Integer groupId;

    private String userName;
    private String phone;
    private Integer sex;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
