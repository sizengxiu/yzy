package com.yzy.service.impl;

import com.yzy.dao.FileScanWhiteMacDao;
import com.yzy.service.WhiteMacService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class WhiteMacServiceImpl implements WhiteMacService {

    @Autowired
    private FileScanWhiteMacDao fileScanWhiteMacDao;

    @Override
    public boolean isInWhiteMacList(String mac) {
        if(StringUtils.isEmpty(mac)){
            log.info("未获取到客户端的mac地址");
            return false;
        }
        int count = fileScanWhiteMacDao.isInWhiteMacList(mac);
        return count>0;
    }
}
