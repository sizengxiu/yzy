package com.yzy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzy.dao.FileScanDeviceDao;
import com.yzy.dao.FileScanLogDao;
import com.yzy.model.FileScanDevice;
import com.yzy.model.FileScanLog;
import com.yzy.model.ScanResult;
import com.yzy.service.FileScanService;
import com.yzy.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @user szx
 * @date 2020/11/4 22:27
 */
@Service
public class FileScanServiceImpl implements FileScanService {

    @Autowired
    private FileScanDeviceDao deviceDao;

    @Autowired
    private FileScanLogDao logDao;

    @Override
    public int            saveScanResult(ScanResult result) {
        FileScanDevice device = result.getDevice();
        Date dataTime = device.getDataTime();
        device.setDataTime(null);
        String md5 = MD5Util.getMD5(JSONObject.toJSONString(device));
        device.setMd5(md5);
        int md5Count = deviceDao.getMd5Count(md5);
        //新的设备信息
        if(md5Count==0){
            deviceDao.insert(device);
        }
        device.setDataTime(dataTime);
        //
        List<FileScanLog> list = result.getList();
        //没有扫描到非法文件，则在数据库中插入一条空记录，记录扫描时间
        if(list==null || list.size()==0){
            list=new LinkedList<>();
            FileScanLog log=new FileScanLog();
            list.add(log);
        }
        for(FileScanLog log:list){
            log.setScanTime(device.getDataTime());
            log.setMd5(md5);
        }
        logDao.insertByBatch(list);
        return 0;
    }
}
