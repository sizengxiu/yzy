package com.yzy.service;

import com.yzy.model.DutyResultVo;
import com.yzy.model.YzzbDutyHistory;
import com.yzy.model.YzzbDutyHistoryDto;

import java.util.Date;
import java.util.List;

/**
 *  排班service
 *  @user szx
 * @date 2021/5/17 22:16
 */
public interface DutyPlanService {

    /**
     * 按月进行排班，返回排班后的结果
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/5/17 22:23
     */
    List<DutyResultVo> planByDate(int year, int month);
    /**
     * 获取排班数据
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/27 22:21
     */
    List<DutyResultVo> getPbListByDate(int year, int month);

    /**
     * 检查该时间段内是否有发布的排班数据
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/5/18 21:22
     */
    boolean checkAllowPublishCurrentMonthData(int year,int month);

    /**
     * 发布排班数据
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/27 18:58
     */
    int publishPbData(int year,int month);



    /**
     * 当月数据是否已发布
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/28 21:54
     */
    boolean  isDataPublished(int year,int month);

}
