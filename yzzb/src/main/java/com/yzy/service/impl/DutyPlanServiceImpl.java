package com.yzy.service.impl;

import com.yzy.dao.*;
import com.yzy.exception.BussinessException;
import com.yzy.model.*;
import com.yzy.service.DutyPlanService;
import com.yzy.util.CollectionUtil;
import com.yzy.util.DateUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

/**
 * @user szx
 * @date 2021/5/17 22:16
 */
@Slf4j
@Service
@Transactional
public class DutyPlanServiceImpl implements DutyPlanService {


    @Autowired
    private YzzbDutyCandidateDao candidateDao;

    @Autowired
    private YzzbDutyGroupDao groupDao;
    @Autowired
    private YzzbDutyGroupUserDao groupUserDao;

    @Autowired
    private YzzbDutyZbDao zbDao;

    private int period = 0;

    @Override
    public List<DutyResultVo> getPbListByDate(int year, int month) {
        Date startDate = DateUtil.getMonthFirstDay(year, month);
        Date endDate = DateUtil.getMonthLastDay(year, month);
        List<DutyResultVo> list = zbDao.getPbListByDate(startDate, endDate);
        return list;
    }

    @Override
    public List<DutyResultVo> planByDate(int year, int month) {
        //1.合法性检测
        checkAllowPbCurrentMonthData(year, month);

        Date startDate = DateUtil.getMonthFirstDay(year, month);
        Date endDate = DateUtil.getMonthLastDay(year, month);

        //2.获取后续值班人员列表以及分组信息
        Map<Integer, Set<Integer>> groupMap = new HashMap<>();//用户分组信息
        Map<Integer, Map<Integer, List<Integer>>> candidateMap = new HashMap<>();//候选值班员信息按值班周几分类
        Map<Integer, YzzbDutyCandidateVo> candidateDic = new HashMap<>();//候选值班员信息
//        Map<Integer,List<Integer>> sexCandidateMap=new HashMap<>();//男女分开

        //平均隔多少天参加一次值班；其中减14 是为了极端情况下会差14天
        //人员不可能正好够一次排班的，比如人数第一次排班时，并不够40天的，因为人员是均匀分散在周一到周天的，实际排时是按7的倍数排的

        period = candidateDao.getCadidateCount() / 3 - 14;
        log.info("{}年{}月排班，每隔{}天值班一次", year, month, period);
        List<YzzbDutyCandidateVo> candidateList = candidateDao.getCandidateVoList(endDate, period);
        if (CollectionUtil.isEmpty(candidateList)) {
            log.warn("如果是第一次执行碰到这个错误，请注意是否是忘了修改时间：update yzzb_duty_candidate set last_duty_date='2021-07-15'");
            throw new BussinessException("未查询到候选的值班员，请确认值班员是否为空！");
        }

        if (candidateList.size() < 90) {
            throw new BussinessException("员工人数不足！");
        }

        for (YzzbDutyCandidateVo candidate : candidateList) {
            Integer currentIndexWeek = candidate.getCurrentIndexWeek();
            Integer id = candidate.getEmployeeId();
            int sex = candidate.getSex();
            //1.放入dic
            candidateDic.put(id, candidate);
            //2.按值班周几分组
            Map<Integer, List<Integer>> eachSexCandidateMap = candidateMap.get(currentIndexWeek);
            if (eachSexCandidateMap == null) {
                eachSexCandidateMap = new HashMap<>();
                candidateMap.put(currentIndexWeek, eachSexCandidateMap);
            }
            List<Integer> eachCandidateList = eachSexCandidateMap.get(sex);
            if (eachCandidateList == null) {
                eachCandidateList = new LinkedList<>();
                eachSexCandidateMap.put(sex, eachCandidateList);
            }
            eachCandidateList.add(id);

        }

        List<YzzbDutyGroupUser> userGroupList = groupUserDao.getUserGroupList();
        //如果存在分组信息
        if (!CollectionUtil.isEmpty(userGroupList)) {
            for (YzzbDutyGroupUser userGroup : userGroupList) {
                Integer groupid = userGroup.getGroupid();
                Set<Integer> groupUserSet = groupMap.get(groupid);
                if (groupUserSet == null) {
                    groupUserSet = new HashSet<>();
                    groupMap.put(groupid, groupUserSet);
                }
                groupUserSet.add(groupid);
            }
        }
        //3.组织成前端需要的数据结构
        List<DutyResultVo> resultList = new LinkedList<>();

        //已经参与排班的人
        Set<Integer> alreadyUsedUserSet = new HashSet<>();

        int monthDays = DateUtil.getMonthDays(year, month);
        int weekIndex = DateUtil.getFirstDayWeekIndex(year, month);

        DutyResultVo dutyResultVo = new DutyResultVo();
        for (int i = 1; i <= monthDays; weekIndex++, i++) {
            Date currentDate = DateUtil.addDateByDay(startDate, i-1);
            List<Integer> list = selectCandidate(groupMap, candidateMap, alreadyUsedUserSet, weekIndex, currentDate,candidateDic,  false);
            setCandidateInfo(dutyResultVo, i, weekIndex, list, candidateDic, resultList);
            if (weekIndex == 7) {
                weekIndex = 0;
            }
        }

        addPbResultToDb(resultList, year, month);
        return resultList;
    }


    /**
     * 把周一到周天的结果扔到map中去
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/6 17:55
     */
    private Map<Integer, DayDutyResultVo> getDayDutyResultVoMap(YzzbDutyResult yzzbDutyResult) {
        Map<Integer, DayDutyResultVo> dic = new HashMap<>();
        //按月排班，会生成30个对象，采用原型模式
        DayDutyResultVo dayDutyResultVo = new DayDutyResultVo();
        yzzbDutyResult.setMonday(dayDutyResultVo.clone());
        yzzbDutyResult.setTuesday(dayDutyResultVo.clone());
        yzzbDutyResult.setWednesday(dayDutyResultVo.clone());
        yzzbDutyResult.setThursday(dayDutyResultVo.clone());
        yzzbDutyResult.setFriday(dayDutyResultVo.clone());
        yzzbDutyResult.setSaturday(dayDutyResultVo.clone());
        yzzbDutyResult.setSunday(dayDutyResultVo.clone());
        dic.put(1, yzzbDutyResult.getMonday());
        dic.put(2, yzzbDutyResult.getTuesday());
        dic.put(3, yzzbDutyResult.getWednesday());
        dic.put(4, yzzbDutyResult.getThursday());
        dic.put(5, yzzbDutyResult.getFriday());
        dic.put(6, yzzbDutyResult.getThursday());
        dic.put(7, yzzbDutyResult.getSunday());
        return dic;
    }

    /**
     * 选择一天的3个值班员
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/25 16:10
     */
    private List<Integer> selectCandidate(Map<Integer, Set<Integer>> groupMap, Map<Integer, Map<Integer, List<Integer>>> candidateMap,
                                          Set<Integer> alreadyUsedUserSet, int weekIndex, Date currentDate,
                                          Map<Integer, YzzbDutyCandidateVo> candidateDic,
                                          boolean alreadyContainsFemale) {
        List<Integer> resultList = new LinkedList<>();//排班结果
        //首选选择一个女的
        if (!alreadyContainsFemale) {
            selectFemaleCandidate(groupMap, candidateMap, alreadyUsedUserSet, weekIndex, currentDate, candidateDic, resultList);
        }
        alreadyContainsFemale = true;
        int count = resultList.size();
        if (count == 3) {
            return resultList;
        }
        List<Integer> candidateList = candidateMap.get(weekIndex).get(1);
        Iterator<Integer> iterator = candidateList.iterator();
        while (iterator.hasNext() && count < 3) {
            Integer id = iterator.next();
            //1.候选人是否合法
            if (!isIllegalCandidate(alreadyUsedUserSet, currentDate, candidateDic, iterator, id)) {
                continue;
            }
            //2.选择用户后，并从和用户同一个组的人员中选择另外的排班人员;
            YzzbDutyCandidateVo candidate = candidateDic.get(id);
            Integer groupId = candidate.getGroupId();
            //当前选择的人员不在分组中直接选择（不管是选择第几个人，只要他不在分组中都可以选）
            if (groupId == null) {
                resultList.add(id);
                alreadyUsedUserSet.add(id);
                iterator.remove();
                count++;
                continue;
            }
            //3.如果排的是第2个值班员，则不选择在分组中，且分组中人数为3的人
            if (count == 1) {
                Set<Integer> groupSet = groupMap.get(groupId);
                if (groupSet.size() > 2) {
                    continue;
                }
                //改分组中只有两个人，把另一个人也选上
                resultList.add(id);
                alreadyUsedUserSet.add(id);
                count++;
                Iterator<Integer> groupItr = groupSet.iterator();
                while (groupItr.hasNext()) {
                    Integer anotherId = groupItr.next();
                    if (!anotherId.equals(id) && !alreadyUsedUserSet.contains(anotherId)) {
                        resultList.add(anotherId);
                        alreadyUsedUserSet.add(anotherId);
                        count++;
                    }
                }
            }
            //4.如果是排的第三个值班员，则不选择在分组中的人,跳过这个候选人
            if (count == 2) {
                continue;
            }

            if (count >= 3) {
                break;
            }
        }
        //count<3，说明循环了一遍了，因为分组规则限制找不到候选人了，分以下几种情况：
        //      1.选了一个人，则剩余的人全都是3人分组，此时需要把这个人去掉，然后重新选择
        //      2.选了两个人，又分两种情况：
        //        2.1这两个人一个组，则此时剩余的人全都在分组中，为了保护分组规则，也为了简单起见，抛出异常给管理员提醒
        //        2.2这两个人都没有分组，则此时剩余的人也全都在分组中，处理方式同上(简单粗暴，即使不这样处理，无非只能多排两天)
        //      3.人员全部分完了，鉴于公司已有人数，暂不考虑这种情况
        if (count < 3) {
            if (count == 2) {
                throw new BussinessException("分组过多，不拆分组的情况下，无法继续排班！");
            }
            if (count == 0) {
                throw new BussinessException("候选人不足！");
            }
            Integer id = resultList.get(0);
            alreadyUsedUserSet.add(id);
            return selectCandidate(groupMap, candidateMap, alreadyUsedUserSet, weekIndex, currentDate,candidateDic, alreadyContainsFemale);
        }

        iterator = candidateList.iterator();
        int deteleCount = 0;
        while (iterator.hasNext() && deteleCount < 3) {
            int id = iterator.next();
            for (int selecteduserId : resultList) {
                if (id == selecteduserId) {
                    iterator.remove();
                    deteleCount++;
                    break;
                }
            }
        }


        return resultList;
    }

    /**
     * 首先选择女性候选人
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/30 19:40
     */
    private void selectFemaleCandidate(Map<Integer, Set<Integer>> groupMap, Map<Integer, Map<Integer, List<Integer>>> candidateMap,
                                       Set<Integer> alreadyUsedUserSet, int weekIndex, Date currentDate,
                                       Map<Integer, YzzbDutyCandidateVo> candidateDic,
                                       List<Integer> resultList) {
        int count = 0;
        List<Integer> candidateList = candidateMap.get(weekIndex).get(0);
        if (CollectionUtil.isEmpty(candidateList)) {
            return;
        }
        Iterator<Integer> iterator = candidateList.iterator();
        Integer candidateId = null;
        while (iterator.hasNext()) {
            candidateId = iterator.next();
            if (isIllegalCandidate(alreadyUsedUserSet, currentDate, candidateDic, iterator, candidateId)) {
                count++;
                break;
            }
        }
        if (count == 0) {
            return;
        }
        resultList.add(candidateId);
        alreadyUsedUserSet.add(candidateId);
        candidateList.remove(0);
        //2.选择用户后，并从和用户同一个组的人员中选择另外的排班人员;
        YzzbDutyCandidateVo candidate = candidateDic.get(candidateId);
        Integer groupId = candidate.getGroupId();
        Set<Integer> groupSet = groupMap.get(groupId);
        if (CollectionUtil.isEmpty(groupSet)) {
            return;
        }
        Iterator<Integer> groupItr = groupSet.iterator();
        while (groupItr.hasNext()) {
            Integer anotherId = groupItr.next();
            if (!anotherId.equals(candidateId) && !alreadyUsedUserSet.contains(anotherId)) {
                resultList.add(anotherId);
                alreadyUsedUserSet.add(anotherId);
                count++;
            }
        }

    }

    /**
     * 判断候选人的合法性
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/30 20:36
     */
    boolean isIllegalCandidate(Set<Integer> alreadyUsedUserSet,
                               Date currentDate,
                               Map<Integer, YzzbDutyCandidateVo> candidateDic,
                               Iterator<Integer> iterator,
                               int candidateId) {
        if (alreadyUsedUserSet.contains(candidateId)) {
            iterator.remove();
            return false;
        }
        Date lastDutyDate = candidateDic.get(candidateId).getLastDutyDate();
        int differentDays = DateUtil.getDifferentDays(lastDutyDate, currentDate);
        if (differentDays < period) {
            return false;
        }
        return true;
    }


    /**
     * 设置 值班用户信息
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/25 16:15
     */
    private void setCandidateInfo(DayDutyResultVo dayDutyResultVo, List<Integer> list, Map<Integer, YzzbDutyCandidateVo> dic) {
        Integer id = list.get(0);
        YzzbDutyCandidateVo yzzbDutyCandidateVo = dic.get(id);
        dayDutyResultVo.setEmployeeAName(yzzbDutyCandidateVo.getUserName());
        dayDutyResultVo.setEmployeeAphone(yzzbDutyCandidateVo.getPhone());


        id = list.get(1);
        yzzbDutyCandidateVo = dic.get(id);
        dayDutyResultVo.setEmployeeBName(yzzbDutyCandidateVo.getUserName());
        dayDutyResultVo.setEmployeeBphone(yzzbDutyCandidateVo.getPhone());

        id = list.get(2);
        yzzbDutyCandidateVo = dic.get(id);
        dayDutyResultVo.setEmployeeCName(yzzbDutyCandidateVo.getUserName());
        dayDutyResultVo.setEmployeeCphone(yzzbDutyCandidateVo.getPhone());
    }


    /**
     * 设置 值班用户信息
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/25 16:15
     */
    private List<DutyResultVo> setCandidateInfo(DutyResultVo dutyResultVo, int day, int weekIndex, List<Integer> list, Map<Integer, YzzbDutyCandidateVo> dic, List<DutyResultVo> resultList) {
        for (Integer id : list) {
            DutyResultVo resultVo = dutyResultVo.clone();
            YzzbDutyCandidateVo yzzbDutyCandidateVo = dic.get(id);
            resultVo.setDay(day);
            resultVo.setName(yzzbDutyCandidateVo.getUserName());
            resultVo.setPhone(yzzbDutyCandidateVo.getPhone());
            resultVo.setSex(yzzbDutyCandidateVo.getSex());
            resultVo.setWeekIndex(weekIndex);
            resultVo.setUserId(id);
            resultList.add(resultVo);
        }
        return resultList;
    }


    /**
     * 当前月份数据是否允许发布
     * 1.当前及后续月份数据已发布，则不允许发布
     * 2.当前月份尚未预排班，不允许发布
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/27 20:32
     */
    @Override
    public boolean checkAllowPublishCurrentMonthData(int year, int month) {

        checkAllowPbCurrentMonthData(year, month);
        Date startDate = DateUtil.getMonthFirstDay(year, month);
        Date endDate = DateUtil.getMonthLastDay(year, month);

        int count = zbDao.getPbCountByDate(startDate, endDate);
        if (count == 0) {
            throw new BussinessException("当前月份尚未进行预排班，请先点击“预排班”按钮进行排班！", 2);
        }
        return true;
    }

    /**
     * 当前月份使用允许预排班
     * 1.当前月份数据已发布
     * 2.当前后续月份存在已发布的数据
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/27 20:37
     */
    public boolean checkAllowPbCurrentMonthData(int year, int month) {
        Date startDate = DateUtil.getMonthFirstDay(year, month);

        int count = zbDao.getPublishedDataCountByStartDate(startDate);
        if (count > 0) {
            throw new BussinessException("当前月份存在已经发布的排班数据，不允许重新排班！", 1);
        }
        return true;
    }


    /**
     * 将新排班的数据记录到表yzzb_duty_zb中，
     * publish_state置0,未发布
     * state 置1，数据有效
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/18 21:49
     */
    private int addPbResultToDb(List<DutyResultVo> resultList, int year, int month) {
        if (CollectionUtil.isEmpty(resultList)) {
            return 0;
        }
        Date date = new Date();
        //1.先清除旧数据，可能上次排班未发布，此次重新排班
        deleteZbListByDate(year, month, date);
        //2.插入新数据
        List<YzzbDutyZb> zbList = new LinkedList<>();
        YzzbDutyZb zb = new YzzbDutyZb();
        zb.setInsertTime(date);
        zb.setUpdateTime(date);
        zb.setPublishState(0);
        zb.setState(1);
        Calendar calendar = Calendar.getInstance();
        for (DutyResultVo vo : resultList) {
            YzzbDutyZb zbClone = zb.clone();
            calendar.set(year, month - 1, vo.getDay());
            zbClone.setDate(calendar.getTime());//此处vo.date未设值
            zbClone.setWeekIndex(vo.getWeekIndex());
            zbClone.setUserId(vo.getUserId());
            zbList.add(zbClone);
        }

        return zbDao.addZbListByBatch(zbList);
    }


    /**
     * 按时间删除数据（逻辑删除）
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/18 22:21
     */
    private int deleteZbListByDate(int year, int month, Date updateDate) {
        Date startDate = DateUtil.getMonthFirstDay(year, month);
        Date endDate = DateUtil.getMonthLastDay(year, month);
        return zbDao.deleteZbListByDate(startDate, endDate, updateDate);
    }


    @Override
    public int publishPbData(int year, int month) {
        checkAllowPublishCurrentMonthData(year, month);
        //1.将zb表中数据的发布状态置1
        Date startDate = DateUtil.getMonthFirstDay(year, month);
        Date endDate = DateUtil.getMonthLastDay(year, month);
        zbDao.publishPbDataByDate(startDate, endDate, new Date());
        //2.更新候选人信息
        //      1.遍历候选人列表
        //      2.如果本月未参与排班，则将其从列表中删除（列表只保存要更新的数据）
        //      3.如果本月参与排班，则更新其排班的相关信息
        //      4.更新列表到数据库
        //候选人列表
        List<YzzbDutyCandidate> candidateList = candidateDao.getCandidateList();
        //排班数据列表
        List<DutyResultVo> pbList = zbDao.getPbListByDate(startDate, endDate);

        //为了快速检测候选人是否要从列表中删除，构造Map
        Map<Integer, DutyResultVo> pbMap = new HashMap<>(128);
        for (DutyResultVo vo : pbList) {
            pbMap.put(vo.getUserId(), vo);
        }

        Date date = new Date();
        Iterator<YzzbDutyCandidate> candidateItr = candidateList.iterator();
        while (candidateItr.hasNext()) {
            YzzbDutyCandidate candidate = candidateItr.next();
            DutyResultVo dutyResultVo = pbMap.get(candidate.getEmployeeId());
            if (dutyResultVo == null) {
                candidateItr.remove();
                continue;
            }

            int nextWeekIndex = dutyResultVo.getWeekIndex() == 7 ? 1 : dutyResultVo.getWeekIndex() + 1;
            candidate.setCurrentIndexWeek(nextWeekIndex);
            candidate.setLastDutyDate(dutyResultVo.getDate());
            candidate.setLastIndexWeek(dutyResultVo.getWeekIndex());
            candidate.setUpdateTime(date);
        }
        candidateDao.updateCandidateZbInfoByBatch(candidateList);

        return 0;
    }

    @Override
    public boolean isDataPublished(int year, int month) {
        Date startDate = DateUtil.getMonthFirstDay(year, month);
        Date endDate = DateUtil.getMonthLastDay(year, month);
        int count = zbDao.getPublishedDataCountByMonth(startDate, endDate);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean initFirstZbDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -2);
        candidateDao.initFirstZbDate(calendar.getTime());
        return false;
    }
}
