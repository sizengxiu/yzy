package com.yzy.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @user szx
 * @date 2020/11/1 11:01
 */
@Slf4j
public class FileUtil {


    /**
     * 读取配置文件
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 11:26
     */
    public static Properties getPropertis(String path) {

        Properties prop = new Properties();
        try (InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(path)) {
            prop.load(new InputStreamReader(inputStream, "utf-8"));
        } catch (IOException e) {
            log.error("配置文件读取异常", e);
        }
        return prop;
    }

    public static void updateProperties(Properties prop,String path){
        //文件输出流
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            //将Properties集合保存到流中
            prop.store(fos, "Copyright (c) Boxcode Studio");
            fos.close();//关闭流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 内容输出到文件
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 15:13
     */
    public static boolean writeFile(String content,String path, String fileName,boolean append) {
        FileWriter fileWritter = null;
        BufferedWriter bufferedWriter=null;
//        fileName=resultDir+File.separator+fileName;
        try {
            File file = new File(path,fileName);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            //使用true，即进行append file
//            fileWritter = new FileWriter(file, append);
//            fileWritter.write(content);
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,append), "utf-8"));
            bufferedWriter.write(content);
            log.debug("内容输出到文件，【content={},fileName={}】",content, fileName);
        } catch (IOException e) {
            log.error("文件输出异常,【content={},fileName={}】,异常详细：{}", content, fileName, e);
            return false;
        }finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    log.error("文件输出流关闭异常,【content={},fileName={}】,异常详细：{}", content, fileName, e);
                }
            }
        }
        return true;
    }


    /**
     * 读取文件
     * @param fileName
     * @return
     */
    public static String readFile(String fileName,boolean notExistCreate) {
        StringBuilder sb=new StringBuilder();
        try(BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));) {
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        }catch(FileNotFoundException e){
            if(notExistCreate) {
                File file = new File(fileName);
                if (!file.exists()) {
                    log.info("要读取的文件{}不存在，创建文件！",fileName);
                    try {
                        log.info(file.getParentFile().isDirectory()+"");
                        log.info(file.getParentFile().exists()+"");
                        if(file.getParentFile().isDirectory() && !file.getParentFile().exists()){
                            log.info("{}父目录不存在，进行创建！",file.getParentFile().getAbsolutePath());
                            file.getParentFile().mkdirs();
                        }
                        file.createNewFile();
                        log.info("文件创建成功！");
                        return readFile(fileName,false);
                    } catch (IOException e1) {
                        log.error("{}文件读取发现文件不存在，创建文件失败：{}", fileName, e);
                    }
                }
            }
        }catch (IOException e) {
            log.error("{}文件读取异常：{}",fileName,e);
        }
        return sb.toString();
    }
    /**
     * 获取磁盘目录
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 16:52
     */
    public  static List<String> getSystemDiskID() {
        File[] files = File.listRoots();
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i < files.length; i++) {
            list.add(files[i].toString());
        }
        return list;
    }


    /**
     * 创建目录
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 17:26
     */
    public static void createDir(String path,String dirName){
        File file = new File(path,dirName);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 获取硬盘序列号
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 17:38
     */
    public static String getHdSerialInfo() {

        String line = "";
        String HdSerial = "";//定义变量 硬盘序列号
        try {
            Process proces = Runtime.getRuntime().exec("cmd /c dir c:");//获取命令行参数
            BufferedReader buffreader = new BufferedReader(new InputStreamReader(proces.getInputStream(),"gbk"));

            while ((line = buffreader.readLine()) != null) {
                if (line.indexOf("卷的序列号是 ") != -1) {  //读取参数并获取硬盘序列号

                    HdSerial = line.substring(line.indexOf("卷的序列号是 ") + "卷的序列号是 ".length(), line.length());
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return HdSerial;
    }

    /**
     * 获取mac地址
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/1 17:40
     */
    public static String getMacAddr() {
        StringBuilder sb = new StringBuilder();
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");


            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
