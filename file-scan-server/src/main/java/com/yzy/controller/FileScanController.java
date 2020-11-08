package com.yzy.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzy.model.FileScanDevice;
import com.yzy.model.IllegalFileInfo;
import com.yzy.model.Result;
import com.yzy.model.ScanResult;
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

}
