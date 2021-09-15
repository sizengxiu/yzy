package com.yzy.config;

import com.yzy.intercepter.LoginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @user szx
 * @date 2021/5/30 22:56
 */
//@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    // 将登录拦截器配置到容器中
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/",  "/login/**","/common/**");
    }
}
