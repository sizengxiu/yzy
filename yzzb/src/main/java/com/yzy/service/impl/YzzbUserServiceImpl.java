package com.yzy.service.impl;

import com.yzy.dao.UserDao;
import com.yzy.dao.YzzbDutyCandidateDao;
import com.yzy.exception.BussinessException;
import com.yzy.model.User;
import com.yzy.model.UserCountVo;
import com.yzy.model.UserVo;
import com.yzy.model.YzzbDutyCandidate;
import com.yzy.service.YzzbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @user szx
 * @date 2021/5/19 20:34
 */
@Service
public class YzzbUserServiceImpl implements YzzbUserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private YzzbDutyCandidateDao candidateDao;
    @Override
    public int addUserByBatch(List<User> list) {
        int size = list.size();
        if(size ==0){
            return 0;
        }
        //检查员工编号是否和已有的员工重复
        List<String> existCodeList = userDao.getExistCodeList(list);
        if(existCodeList !=null && existCodeList.size()!=0){
            throw new BussinessException("工号："+existCodeList.toString()+"已存在，请检查后重新上传！");
        }

        List<YzzbDutyCandidate> candidateList=new LinkedList<>();
        YzzbDutyCandidate candidate=new YzzbDutyCandidate();
        setCandidateInitValue(candidate);


        //每次新加员工都均匀分散在每周的每天
        int maxUserId = userDao.getMaxUserId();

        int userId=maxUserId;
        int weekIndex=1;

        //为解决因分组导致的人员在周一到周天之间的数据分配不均匀问题，添加员工时做如下处理：
        //1.先根据现有员工的分布情况，把周一到周天补均匀
        List<UserCountVo> userCountVoList = candidateDao.getUserCountGroupByWeekIndex();

        Iterator<User> itr = list.iterator();
        if(userCountVoList.size()>1){
            //1.先计算把周一到周日的员工分布补齐，每天需要补充的人数
            calculateAjustCount(userCountVoList);
            for(UserCountVo userCountVo:userCountVoList){
                int userCount = userCountVo.getUserCount();
                weekIndex=userCountVo.getWeekIndex();
                if(userCount==0){
                    continue;
                }
                for(int j=1;j<=userCount && itr.hasNext();j++){
                    User user=itr.next();
                    userId++;
                    addCandidateToList(candidate,userId,candidateList,weekIndex,user);
                }
                if(!itr.hasNext()){
                    break;
                }
            }
        }

        weekIndex=1;
        //2.剩下的员工均匀分布在周一到周天
        while(itr.hasNext()){
            User user = itr.next();
            userId++;
            addCandidateToList(candidate,userId,candidateList,weekIndex,user);
           weekIndex= weekIndex%7+1;
        }
        userDao.addUserByBatch(list);
        candidateDao.addCandidateByBatch(candidateList);
        return 0;
    }


    /**
     * 设定初始值
     * @param candidate
     */
    private void setCandidateInitValue(YzzbDutyCandidate candidate){
        Date date = new Date();
        candidate.setCreateTime(date);
        candidate.setAttendDuty(1);
        candidate.setState(1);
        candidate.setUpdateTime(date);
        candidate.setUserState(1);
        candidate.setLastDutyDate(date);
    }

    /**
     * 添加候选到候选列表（抽取重复代码）
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/30 22:35
     */
    private void addCandidateToList(YzzbDutyCandidate candidate,int userId,
                                    List<YzzbDutyCandidate> candidateList,int weekIndex,
                                    User user){
        user.setId(userId);
        YzzbDutyCandidate candidateClone=candidate.clone();
        candidateClone.setEmployeeId(userId);
        candidateClone.setCurrentIndexWeek(weekIndex);
        candidateList.add(candidateClone);
    }

    /**
     * 计算把周一到周日每天的员工人数补充到均匀各自需要的人数
     *   list是按人数从小到大排列的
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/30 22:10
     */
    private void calculateAjustCount(List<UserCountVo> list){
        int size = list.size();
        if(size<2){
            return ;
        }
        int maxCount=list.get(size-1).getUserCount();
        for(UserCountVo userCountVo:list){
            int userCount = userCountVo.getUserCount();
            userCountVo.setUserCount(maxCount-userCount);
        }
    }

    @Override
    public List<UserVo> getAllUsers() {
        return userDao.getAllUsers();
    }
}
