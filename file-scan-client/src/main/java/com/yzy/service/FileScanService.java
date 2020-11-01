package com.yzy.service;

import com.alibaba.fastjson.JSONObject;
import com.yzy.model.IllegalFileInfo;
import com.yzy.util.DateUtil;
import com.yzy.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @user szx
 * @date 2020/10/31 21:07
 */
@Component
@Slf4j
public class FileScanService implements CommandLineRunner {

    private String dirName;

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    /**
     * 加载关键词信息
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 14:53
     */
    public Set<String> loadKeyWords(String path) {
        Set<String> set = new HashSet<>(100);
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String strLine = null;
            int lineCount = 1;
            while (null != (strLine = bufferedReader.readLine())) {
                log.debug("第[" + lineCount + "]行数据:[" + strLine + "]");
                lineCount++;
                set.add(strLine);
            }
        } catch (Exception e) {
            log.error("文件读取异常", e);
        }
        return set;
    }

    /**
     * 扫描非法文件
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 16:58
     */
    public void scanIllegalFile(Set<String> words,List<File> fileList) {
//        setDirName(LocalDateTime.now().toString());
        log.info("扫描开始时间：{}", getDirName());
        log.info("扫描开始时间：{}", LocalDateTime.now());

        //存储扫描结果
        List<String> resultList = new ArrayList<String>();
        for (File file : fileList) {
            traverseFiles(file, words, resultList);
        }
        log.info("扫描结束时间：{}", LocalDateTime.now());
        saveScanResult(resultList);
    }





    /**
     * 遍历指定目录（此处传入盘符）
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 16:58
     */
    private void traverseFiles(File dir, Set<String> words, List<String> list) {
        //获取指定目录下当前的所有文件或文件夹对像
        File[] files = dir.listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isDirectory()) {
//                System.out.println(file.getAbsolutePath());
                traverseFiles(file, words, list);
            } else {
                for (String word : words) {
                    if (file.getName().contains(word)) {
                        log.info("检测到非法文件名：{}", file.getAbsolutePath());
                        list.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    /**
     * 保存扫描结果到本地文件
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 17:50
     */
    private void saveScanResult(List<String> list) {
        Properties prop = FileUtil.getPropertis("param.properties");
        String serialInfo = FileUtil.getHdSerialInfo();
        String macAddr = FileUtil.getMacAddr();
        IllegalFileInfo info = new IllegalFileInfo();
        info.setDiskSerial(serialInfo);
        info.setMac(macAddr);
        info.setCode(prop.getProperty("code"));
        info.setResponsePerson(prop.getProperty("responsePerson"));
        info.setScanTime(new Date());
        info.setUseScope(Integer.valueOf(prop.getProperty("useScope")));
        info.setSecretLevel(Integer.valueOf(prop.getProperty("secretLevel")));
        info.setFiles(list);
        String scanResultStr = JSONObject.toJSONString(info);
        FileUtil.writeFile(scanResultStr, DateUtil.getToday());
    }

    @Override
    public void run(String... args) throws Exception {
       /* Properties propertis = FileUtil.getPropertis("param.properties");
        log.info(propertis.toString());
        Set<String> keyWords = loadKeyWords("keyWord.txt");
        log.info(keyWords.toString());
        scanIllegalFile(keyWords);
        FileUtil.writeFile("测试", "123.txt");*/

        //开始
        Properties prop = FileUtil.getPropertis("param.properties");
        //运行模式
        int workMode = Integer.valueOf(prop.getProperty("workMode"));
        //上次运行时间
        String lastScanTime = prop.getProperty("lastScanTime");
        //指定目录
        String dir = prop.getProperty("dir");
        //以下是判断按照指定目录扫描还是全盘扫描
        List<File> fileList=new ArrayList<>();
        if(!StringUtils.isEmpty(dir)){
            File file=new File(dir);
            if(!file.exists()){
                log.error("指定目录不存在【dir={}】,程序直接退出！！！",dir);
                System.exit(-1);
            }
            fileList.add(file);
        }else{
            fileList.addAll(Arrays.asList(File.listRoots()));
        }

        //0定时扫描，1立刻扫描（只执行一次）
        if(workMode==0){
            runByTimer(lastScanTime,fileList);
            log.info("workMode=0,进行定时扫描");
        }else{
            log.info("workMode=1,立刻进行扫描");
            runRightNow(fileList);
        }

    }



    /**
     * 定时扫描
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 18:37
     */
    public void runByTimer(String lastScanTime,  List<File> fileList) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12); // 控制时
        calendar.set(Calendar.MINUTE, 0);       // 控制分
        calendar.set(Calendar.SECOND, 0);       // 控制秒

        Date time = calendar.getTime();         // 得出执行任务的时间,此处为今天的12：00：00

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                doRunByTimer(lastScanTime, fileList);
            }
        }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
    }

    /**
     * 实际干活的函数
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 18:37
     */
    private void doRunByTimer(String lastScanTime,  List<File> fileList) {
        if (!StringUtils.isEmpty(lastScanTime)) {
            LocalDateTime lastDate = LocalDateTime.parse(lastScanTime);
            //不够七天直接结束
            if (!lastDate.plusDays(6).isAfter(LocalDateTime.now())) {
                log.info("检测到上次扫描时间为：{}，间隔未到7天，不执行扫描任务", lastScanTime);
                return;
            }
        }
        runRightNow(fileList);
    }

    /**
     * 立刻（单次）执行扫描任务
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 18:39
     */
    public void runRightNow(List<File> fileList) {
        Set<String> keyWords = loadKeyWords("keyWord.txt");
        scanIllegalFile(keyWords,fileList);
    }



}
