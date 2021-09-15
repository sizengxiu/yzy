package com.yzy.service.impl;

import com.yzy.dao.BlackIpListDao;
import com.yzy.service.BlackIpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class BlackIpServiceImpl implements BlackIpService {
    @Autowired
    private BlackIpListDao blackIpListDao;

    @Override
    public int saveBlackVisitRecord(String ip, String mac) {
        return blackIpListDao.saveBlackVisitRecord(ip,mac);
    }
}
