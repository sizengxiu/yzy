package com.yzy.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzy.model.DutyResultVo;
import com.yzy.model.KeyValueEntity;
import com.yzy.model.Result;
import com.yzy.model.UserVo;
import com.yzy.service.DutyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * @user szx
 * @date 2021/5/18 20:54
 */
@RestController
@RequestMapping("duty")
public class YzzbDutyController {

    @Autowired
    private DutyPlanService dutyPlanService;

    /**
     * 当月是否存在已发布的排版数据
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/26 20:45
     */
    @RequestMapping("checkIfPublishedByDate")
    public Result checkIfPublishedByDate(@RequestParam("year") int year, @RequestParam("month") int month){
        boolean success = dutyPlanService.checkIfPublishedByDate(year, month);
        Result result = new Result();
        result.setSuccess(success);
        return result;
    }

    /**
     * 获取年份下拉列表
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/27 21:25
     */
    @RequestMapping("getZbYearList")
    public Result getZbYearList(){
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        List<KeyValueEntity> list=new LinkedList<>();
        for(int i=2020;i<=year+1;i++){
            list.add(new KeyValueEntity<Integer,Integer>(i,i));
        }
        return Result.getSuccessResult(list);
    }
    /**
     * 获取月份下拉列表
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/27 22:02
     */
    @RequestMapping("getZbMonthList")
    public Result getZbMonthList(){
        List<KeyValueEntity> list=new LinkedList<>();
        for(int i=1;i<=12;i++){
            list.add(new KeyValueEntity<Integer,Integer>(i,i));
        }
        return Result.getSuccessResult(list);
    }


    /**
     * 按月排班
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/29 21:53
     */
    @RequestMapping("planByDate")
    public Result planByDate(@RequestParam("year") int year,@RequestParam("month")int month){
        List<DutyResultVo> list = dutyPlanService.planByDate(year, month);
        return Result.getSuccessResult(list);
    }


    /**
     * 根据日期获取排班数据
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/18 19:41
     */
    @RequestMapping("getPbListByDate")
    public Result getPbListByDate(@RequestParam("year") int year,@RequestParam("month")int month){
        PageHelper.startPage(0,200);
        List<DutyResultVo> list = dutyPlanService.getPbListByDate(year, month);
        PageInfo<DutyResultVo> pageInfo = new PageInfo<>(list);
        return Result.getSuccessResult(pageInfo);
    }


    @RequestMapping("test")
    public String test(String t){
        System.out.println("1");
        return t;
    }
}
