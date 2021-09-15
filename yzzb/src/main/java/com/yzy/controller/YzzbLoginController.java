package com.yzy.controller;

import com.yzy.model.Result;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 登录
 * @user szx
 * @date 2021/5/30 10:27
 *
 */
@RestController
@RequestMapping("login")
@Slf4j
@Validated
public class YzzbLoginController {

    /**
     * 登录
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/5/30 10:33
     */
    @RequestMapping("login")
    public Result login(@NotBlank(message = "密码不能为空！") @RequestParam("password")String password,
                        @NotBlank(message = "密码不能为空！") @RequestParam("username")String username,
                        HttpSession session,
                        HttpServletResponse response)throws Exception {
        log.debug("用户输入的用户名：{},密码：{}",username,password);
        if("admin".equals(username) && "123".equals(password)){
            session.setAttribute("user","admin");
//            response.sendRedirect("/yzzb/zb/home.html");
            return  Result.getSuccessResult("登录成功","/yzzb/zb/home.html");
        }
        return Result.getErrorResult(200,"用户名或密码不正确！");
    }


    @RequestMapping("test1")
    public String test(String t){
        System.out.println("1");
        return t;
    }
}
