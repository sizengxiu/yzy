package com.yzy.interceptor;

import com.yzy.service.WhiteMacService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 临时用资产编号当做白名单，过滤请求
 * @user szx
 * @date 2020/11/24 23:13
 */
@Slf4j
public class PCCoderInterceptor  implements HandlerInterceptor {

    @Autowired
    private WhiteMacService whiteMacService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(whiteMacService==null){
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            whiteMacService = factory.getBean(WhiteMacService.class);
        }
        String code = request.getParameter("code");
        boolean inWhiteMacList = whiteMacService.isInWhiteMacList(code);
        if(!inWhiteMacList) {
            log.info("客户端资产编号code不在白名单列表中，请求拒绝！", code);
            return false;
        }
        return true;
    }
}
