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
    List<DutyResultVo> getPbListByDate(@Param("date") Date date);
}