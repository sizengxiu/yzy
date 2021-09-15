package com.yzy.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * yzzb_duty_candidate
 * @author 
 */
@Data
public class YzzbDutyCandidate implements Serializable,Cloneable {
    private Integer id;

    /**
     * 用户id
     */
    private Integer employeeId;

    /**
     * 当前应周几值班
     */
    private Integer currentIndexWeek;

    /**
     * 上次值班周几
     */
    private Integer lastIndexWeek;

    /**
     * 当前排班日期
     */
    private Date currentDutyDate;

    /**
     * 上次值班日期
     */
    private Date lastDutyDate;

    /**
     * 是否参加值班
     */
    private Integer attendDuty;

    private Integer weight;

    /**
     * 用户状态（默认在岗1,出差2，离职3，请假4）
     */
    private Integer userState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 有效状态
     */
    private Integer state;

    private static final long serialVersionUID = 1L;

    @Override
    public YzzbDutyCandidate clone(){
        YzzbDutyCandidate candidate=null;
        try {
            candidate=(YzzbDutyCandidate)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return candidate;
    }
}