package com.yzy.controller;

import com.yzy.model.IllegalFileInfo;
import lombok.extern.slf4j.Slf4j;
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

    @RequestMapping("saveScanResult")
    public int saveScanResult(IllegalFileInfo info){
        log.info(info.toString());
        return 0;
    }
}
