package com.yzy.service.impl;

import com.yzy.dao.UserDao;
import com.yzy.dao.YzzbDutyCandidateDao;
import com.yzy.service.TestConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @user szx
 * @date 2021/6/3 20:12
 */
@Service
public class TestConfigSerivceImpl implements TestConfigService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private YzzbDutyCandidateDao candidateDao;
    @Override
    public int deleteAllData() {
        candidateDao.deleteAllCandidate();
        userDao.deleteAllUser();
        return 0;
    }
}
