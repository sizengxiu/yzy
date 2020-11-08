package com.yzy.config;

import org.springframework.stereotype.Component;

@Component
public class ConstantParam {
    /**
     * 失败尝试次数
     */
    public static final int TRYTIMES=3;
    /**
     * 发送失败后，写入文件时数据之间的分隔符
     */
    public static  final String SEPARATOR="@@@@";
    /**
     * 存储发送失败的数据
     */
    public static final  String FAILDATA_FILENAME="failData.txt";

    /**
     * 保存结果的目录
     */
    public static final String RESULT_DIR="result";

    /**
     * 当前工作目录
     * @param:
     * @return:
     * @auther: szx
     * @date: 2020/11/8 17:26
     */
    public static final String CURRENT_DIR=System.getProperty("user.dir");

}
