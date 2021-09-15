package com.yzy.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * yzzb_duty_history
 * @author 
 */
@Data
public class YzzbDutyHistory implements Serializable {
    private Integer id;

    private Integer indexWeek;

    private Date date;

    private Integer employeeA;

    private Integer employeeB;

    private Integer employeeC;

    /**
     * 数据状态（1已发布，0未发布）
     */
    private Integer state;

    private Date publishTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}