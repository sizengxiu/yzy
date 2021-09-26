package com.yzy.dao;

import com.yzy.model.DutyResultVo;
import com.yzy.model.YzzbDutyZb;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface YzzbDutyZbDao {
    int deleteByPrimaryKey(Integer id);

    int insert(YzzbDutyZb record);

    int insertSelective(YzzbDutyZb record);

    YzzbDutyZb selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YzzbDutyZb record);

    int updateByPrimaryKey(YzzbDutyZb record);

    /**
     * 获取排班数据列表
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/28 21:54
     */
    List<DutyResultVo> getPbListByDate(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 按时间删除数据（逻辑删除）
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/18 22:20
     */
    int deleteZbListByDate(@Param("startDate") Date startDate,@Param("endDate")Date endDate,@Param("updateDate") Date updateDate);


    /**
     * 批量添加排班数据
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/18 22:33
     */
    int addZbListByBatch(@Param("list") List<YzzbDutyZb> list);

    /**
     * 获取指定时间段的已发布的排班数
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/5/18 20:28
     */
    int getPublishedDataCountByDate(@Param("startDate")Date start,@Param("endDate")Date end);
}


