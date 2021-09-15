package com.yzy.dao;

import com.yzy.model.YzzbDutyHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface YzzbDutyHistoryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(YzzbDutyHistory record);

    int insertSelective(YzzbDutyHistory record);

    YzzbDutyHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YzzbDutyHistory record);

    int updateByPrimaryKey(YzzbDutyHistory record);

    /**
     * 获取指定时间段的已发布的排班数
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/5/18 20:28
     */
    int getPublishDataCountByDate(@Param("start")Date start,@Param("end")Date end);
}