package com.yzy.interceptor;

import com.yzy.service.BlackIpService;
import com.yzy.service.WhiteMacService;
import com.yzy.util.IpUtil;
import com.yzy.util.MacUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class MacInterceptor implements HandlerInterceptor {
    @Autowired
    private WhiteMacService whiteMacService;
    @Autowired
    private BlackIpService blackIpService;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddr = IpUtil.getIpAddr(request);
//        log.info(ipAddr);

        String mac = MacUtil.getMacByIp2(ipAddr);

        if(whiteMacService==null){
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            whiteMacService = factory.getBean(WhiteMacService.class);
        }
        boolean inWhiteMacList = whiteMacService.isInWhiteMacList(mac);
        if(!inWhiteMacList){
            log.info("客户端ip={},mac={}的mac地址不在白名单列表中，请求拒绝！",ipAddr,mac);
            if(blackIpService==null){
                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                blackIpService = factory.getBean(BlackIpService.class);
            }
            blackIpService.saveBlackVisitRecord(ipAddr,mac);
            return false;
        }
        return true;
    }



}
