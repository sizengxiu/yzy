package com.yzy.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable,Cloneable {
    private Integer id;

    /**
     * 员工编号
     */
    private String code;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 是否有效（删除）
     */
    private Integer state;

    /**
     * 是否停用
     */
    private Integer stop;

    /**
     * 数据时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    private static final long serialVersionUID = 1L;
    @Override
    public User clone(){
        User user=null;
        try {
            user=(User)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return user;
    }
}