package com.yzy.controller;

import com.yzy.model.DutyResultVo;
import com.yzy.model.KeyValueEntity;
import com.yzy.model.Result;
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


    @RequestMapping("test")
    public String test(String t){
        System.out.println("1");
        return t;
    }
}
