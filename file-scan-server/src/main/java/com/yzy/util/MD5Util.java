package com.yzy.util;

import org.springframework.util.DigestUtils;

/**
 * md5工具类
 * @user szx
 * @date 2020/11/4 22:29
 */
public class MD5Util {
    //盐，用于混交md5
    private static final String slat = "&%5123***&&%%$$#@";
    /**
     * 生成md5
     * @param
     * @return
     */
    public static String getMD5(String str) {
        String base = str +"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
