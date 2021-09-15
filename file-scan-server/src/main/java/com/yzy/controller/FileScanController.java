package com.yzy.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzy.model.*;
import com.yzy.service.FileScanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @user szx
 * @date 2020/11/1 21:42
 */
@Slf4j
@RestController
@RequestMapping("fileScan")
public class FileScanController {

    @Autowired
    private FileScanService scanService;
    @RequestMapping("saveScanResult")
    public Result saveScanResult(@RequestBody ScanResult scanResult){
        log.info("接收到扫描结果上报：{}",JSONObject.toJSONString(scanResult));
        scanService.saveScanResult(scanResult);
        return Result.getSuccessResult();
    }
    @RequestMapping("test")
    public Result test(int t){
        log.info("sd:",t);
        return Result.getSuccessResult();
    }
    @RequestMapping("test1")
    public Result test1(@RequestBody BlackIp blackIp){
        log.info("sd:",blackIp);
        return Result.getSuccessResult();
    }


}
