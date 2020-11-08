package com.yzy.service;

import com.yzy.config.ConstantParam;
import com.yzy.model.Result;
import com.yzy.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;

@Service
@Slf4j
public class SendFailService {

    @Autowired
    private FileScanService fileScanService;



    /**
     * 发送失败的数据保存到文件中
     * @param msg
     */
    public void saveFaildDataToFile(String msg,boolean append){
        FileUtil.writeFile(msg,ConstantParam.RESULT_DIR, ConstantParam.FAILDATA_FILENAME,append);
    }

    /**
     * 发送历史发送失败的数据
     */
    public void sendFailData(){
        log.info(System.getProperty("user.dir"));
        String content = FileUtil.readFile(ConstantParam.CURRENT_DIR+File.separator+ConstantParam.RESULT_DIR+ File.separator+ConstantParam.FAILDATA_FILENAME,true);
        if(StringUtils.isEmpty(content)){
            log.info("没有检测到发送失败的数据！");
            return ;
        }
        String[] contents = content.split(ConstantParam.SEPARATOR);
        log.info("检测到有发送失败的数据，尝试重新发送");
        StringBuilder sb=new StringBuilder();
        for(String msg:contents){
            if(StringUtils.isEmpty(msg)){
                continue;
            }
            Result result = fileScanService.sendScanResult(msg);
            if(!result.isSuccess()){
                sb.append(msg).append(ConstantParam.SEPARATOR);
            }
        }
        String msg = sb.toString();
        saveFaildDataToFile(sb.toString(),false);
    }
}
