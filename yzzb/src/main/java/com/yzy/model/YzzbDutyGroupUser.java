package com.yzy.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * yzzb_duty_group_user
 * @author 
 */
@Data
public class YzzbDutyGroupUser implements Serializable {
    private Integer id;

    private Integer groupid;

    private Integer userid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 有效状态
     */
    private Integer state;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}