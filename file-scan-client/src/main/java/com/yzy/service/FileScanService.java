package com.yzy.service;

import com.alibaba.fastjson.JSONObject;
import com.yzy.config.ConstantParam;
import com.yzy.model.IllegalFileInfo;
import com.yzy.model.Result;
import com.yzy.model.ScanResult;
import com.yzy.model.ScanLog;
import com.yzy.util.DateUtil;
import com.yzy.util.FileUtil;
import com.yzy.util.MacUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource({"classpath:param.properties"})
public class FileScanService implements CommandLineRunner {


    private int count;

    @Autowired
    private RestService restService;

    @Autowired
    private SendFailService sendFailService;


    @Value("${server.ip}")
    private String ip;
    @Value("${server.port}")
    private String port;

    private final String path = "/fileScanServer/fileScan/saveScanResult";

    /**
     * 加载关键词信息
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 14:53
     */
    public Set<String> loadKeyWordsByDefault(String path) {
        Set<String> set = new HashSet<>(100);
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
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

    public Set<String> loadKeyWords(String path) {
        String newPath = System.getProperty("user.dir") + File.separator + path;
        Set<String> set = new HashSet<>(100);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(newPath), "utf-8"));) {
            String strLine = null;
            int lineCount = 1;
            while ((strLine = in.readLine()) != null) {
                log.info("第[" + lineCount + "]行数据:[" + strLine + "]");
                lineCount++;
                set.add(strLine);
            }
        } catch (Exception e) {
            log.error("文件读取异常", e);
            return loadKeyWordsByDefault(path);
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
    public void scanIllegalFile(Set<String> words, List<File> fileList) {
        count = 0;
        log.info("扫描开始时间：{}", LocalDateTime.now());

        //存储扫描结果
        List<ScanLog> resultList = new ArrayList<ScanLog>();
        for (File file : fileList) {
            traverseFiles(file, words, resultList);
        }
        log.info("扫描到非法文件个数：{}", resultList.size());
        log.info("扫描文件总个数：{}", count);
        log.info("扫描结束时间：{}", LocalDateTime.now());
        ScanResult result = saveScanResult(resultList);
        String msg = JSONObject.toJSONString(result);
        Result sendResult = sendScanResult(msg);
        if (!sendResult.isSuccess()) {
            log.info("扫描结果发送失败，将本次扫描结果存入本地文件！");
            sendFailService.saveFaildDataToFile(msg + ConstantParam.SEPARATOR, true);
        }
    }


    /**
     * 遍历指定目录（此处传入盘符）
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 16:58
     */
    private void traverseFiles(File dir, Set<String> words, List<ScanLog> list) {
        //获取指定目录下当前的所有文件或文件夹对像
        File[] files = dir.listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isDirectory()) {
//                System.out.println(file.getAbsolutePath());
                if (file.getAbsolutePath().equals("C:\\ProgramData\\Application Data") || file.getAbsolutePath().equals("C:\\Users\\All Users\\Application Data")) {
                    continue;
                }
                traverseFiles(file, words, list);
            } else {
                count++;
                for (String word : words) {
                    if (file.getName().contains(word)) {
                        log.info("检测到非法文件名：{}", file.getAbsolutePath());
                        ScanLog scanResult = new ScanLog();
                        scanResult.setFilePath(file.getAbsolutePath());
                        scanResult.setIllegalWord(word);
                        list.add(scanResult);
                        break;
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
    private ScanResult saveScanResult(List<ScanLog> list) {
        String path = "param.properties";
        Properties prop = FileUtil.getPropertisInCurrentDir(path);
        String serialInfo = FileUtil.getHdSerialInfo();
        String macAddr = MacUtil.getMacAddr();
        IllegalFileInfo info = new IllegalFileInfo();
        info.setDiskSerial(serialInfo);
        info.setMacAddr(macAddr);
        info.setCode(prop.getProperty("code"));
        info.setResponsePerson(prop.getProperty("responsePerson"));
        info.setDataTime(new Date());
        info.setUseScope(Integer.valueOf(prop.getProperty("useScope")));
        info.setSecretLevel(Integer.valueOf(prop.getProperty("secretLevel")));
//        info.setFiles(list);
        //

        ScanResult result = new ScanResult();
        result.setDevice(info);
        result.setList(list);
        String scanResultStr = JSONObject.toJSONString(result);
        FileUtil.writeFile(scanResultStr, ConstantParam.RESULT_DIR, DateUtil.getToday(), true);

        prop = new Properties();
        //更新扫描时间
        prop.setProperty("lastScanTime", DateUtil.getNowTime());
        path = ConstantParam.CURRENT_DIR + File.separator + "lastScanTime.properties";
        FileUtil.updateProperties(prop, path);

        return result;
    }

    /**
     * 发送数据到服务器
     *
     * @param result
     */
    public Result sendScanResult(ScanResult result) {
        String url = "http://" + ip + ":" + port + path;
        String paramStr = JSONObject.toJSONString(result);
        return restService.doPost(url, paramStr, ConstantParam.TRYTIMES);
    }

    /**
     * 发送数据到服务器
     *
     * @param msg
     */
    public Result sendScanResult(String msg) {
        String url = "http://" + ip + ":" + port + path;
        return restService.doPost(url, msg, ConstantParam.TRYTIMES);
    }

    @Override
    public void run(String... args) throws Exception {

        //开始
        Properties prop = FileUtil.getPropertisInCurrentDir("param.properties");
        //运行模式
        int workMode = Integer.valueOf(prop.getProperty("workMode"));
        //指定目录
        String dir = prop.getProperty("dir");
        //以下是判断按照指定目录扫描还是全盘扫描
        List<File> fileList = new ArrayList<>();
        if (!StringUtils.isEmpty(dir) && !"/".equals(dir)) {
            String[] dirs = dir.split(";");
            for (String eachDir : dirs) {
                File file = new File(dir);
                if (!file.exists()) {
                    log.error("指定目录不存在【dir={}】,程序直接退出！！！", dir);
                    System.exit(-1);
                }
                log.info("扫描指定目录：{}", dir);
                fileList.add(file);
            }
        } else {
            log.info("未指定具体的扫描目录，进行全盘扫描！");
            fileList.addAll(Arrays.asList(File.listRoots()));
        }

        //0定时扫描，1立刻扫描（只执行一次）
        if (workMode == 0) {
            runByTimer(fileList);
            log.info("workMode=0,进行定时扫描");
        } else {
            log.info("workMode=1,立刻进行扫描");
            runRightNow(fileList);
        }

    }


    /**
     * 定时扫描
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 18:37
     */
    public void runByTimer(List<File> fileList) {
        Properties prop = FileUtil.getPropertisInCurrentDir("lastScanTime.properties");
        //上次运行时间
        String lastScanTime = prop.getProperty("lastScanTime");
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
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 18:37
     */
    private void doRunByTimer(String lastScanTime, List<File> fileList) {
        if (!StringUtils.isEmpty(lastScanTime)) {
            LocalDateTime lastDate = LocalDateTime.parse(lastScanTime, DateUtil.formatterSecond);
            //不够七天直接结束
            if (lastDate.plusDays(6).isAfter(LocalDateTime.now())) {
                log.info("检测到上次扫描时间为：{}，间隔未到7天，不执行扫描任务", lastScanTime);
                return;
            }
        }
        runRightNow(fileList);
    }

    /**
     * 立刻（单次）执行扫描任务
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 18:39
     */
    public void runRightNow(List<File> fileList) {
        sendFailService.sendFailData();
        Set<String> keyWords = loadKeyWords("keyWord.txt");
        scanIllegalFile(keyWords, fileList);
    }


}
