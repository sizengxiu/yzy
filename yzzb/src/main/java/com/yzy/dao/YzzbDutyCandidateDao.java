package com.yzy.dao;

import com.yzy.model.UserCountVo;
import com.yzy.model.YzzbDutyCandidate;
import com.yzy.model.YzzbDutyCandidateVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YzzbDutyCandidateDao {
    int deleteByPrimaryKey(Integer id);

    int insert(YzzbDutyCandidate record);

    int insertSelective(YzzbDutyCandidate record);

    YzzbDutyCandidate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YzzbDutyCandidate record);

    int updateByPrimaryKey(YzzbDutyCandidate record);

    /**
     * 批量添加值班候选人
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/2 20:53
     */
    int addCandidateByBatch(@Param("list")List<YzzbDutyCandidate> list);
    /**
     * 获取人数最少的星期数
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/2 21:30
     */
    int getLeastWeekIndex();
    /**
     * 删除所有用户信息
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/3 20:09
     */
    int deleteAllCandidate();

    /**
     * 获取候选值班人列表
     * @param:
     * @return: 列表内先按照星期排序，再按照上次值班时间排序
     * @auther: szx
     * @date: 2021/6/6 15:21
     */
    List<YzzbDutyCandidateVo> getCandidateVoList();
    /**
     * 获取所有有效的候选人
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/27 20:04
     */
    List<YzzbDutyCandidate> getCandidateList();

    /**
     * 获取周一到周日每天的人数，结果按人数从少到多升序排列
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/30 21:58
     */
    List<UserCountVo> getUserCountGroupByWeekIndex();

    /**
     * 批量更新候选信息（只更新上次及当前值班信息）
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/27 18:56
     */
    int updateCandidateZbInfoByBatch(@Param("list")List<YzzbDutyCandidate> list);



}