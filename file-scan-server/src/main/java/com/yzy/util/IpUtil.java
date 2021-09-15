package com.yzy.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
public class IpUtil {
    /** 通过HttpServletRequest返回IP地址
     * @param request HttpServletRequest
     * @return ip String
     * @throws Exception
     */
    public static String getIpAddr(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        log.debug("request.getHeader(\"x-forwarded-for\")={}",request.getHeader("x-forwarded-for"));
        log.debug("request.getHeader(\"Proxy-Client-IP\")={}",request.getHeader("Proxy-Client-IP"));
        log.debug("request.getHeader(\"WL-Proxy-Client-IP\")={}",request.getHeader("WL-Proxy-Client-IP"));
        log.debug("request.getHeader(\"HTTP_CLIENT_IP\")={}",request.getHeader("HTTP_CLIENT_IP"));
        log.debug("request.getHeader(\"HTTP_X_FORWARDED_FOR\")={}",request.getHeader("HTTP_X_FORWARDED_FOR"));
        log.debug("request.getRemoteAddr()={}",request.getRemoteAddr());
        Enumeration<String> names = request.getHeaderNames();
        while(names.hasMoreElements()){
            String paraName=(String)names.nextElement();
            log.debug(paraName+": "+request.getHeader(paraName));
        }
        return ip;
    }
}
