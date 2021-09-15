package com.yzy.controller;

import com.yzy.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 * @user szx
 * @date 2021/5/30 22:34
 */
@RestControllerAdvice(basePackages = "com.yzy.controller")
@Slf4j
public class GlobalExceptionAdviceController {

    /**
     * 全局异常处理
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/5/30 22:39
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error("全局异常处理器捕获到异常",e);
        return Result.getErrorResult(e.getMessage());
    }

}
