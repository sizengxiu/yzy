package com.yzy.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * yzzb_duty_group
 * @author 
 */
@Data
public class YzzbDutyGroup implements Serializable {
    /**
     * 分组id
     */
    private Integer id;

    /**
     * 小组名称
     */
    private String name;

    /**
     * 有效状态
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}