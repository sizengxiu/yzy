package com.yzy.controller;

import com.yzy.model.Result;
import com.yzy.service.TestConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台预留接口
 * @user szx
 * @date 2021/6/3 20:19
 */
@RestController
@RequestMapping("test")
public class TestConfigController {
    @Autowired
    private TestConfigService configService;

    /**
     * 删除所有数据
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/3 20:21
     */
    @RequestMapping("deleteAllData")
    public Result deleteAllData(){
        configService.deleteAllData();
        return new Result();
    }
}
