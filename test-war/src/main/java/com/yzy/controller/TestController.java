package com.yzy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @user szx
 * @date 2020/11/22 17:48
 */
@RequestMapping("test")
@RestController
public class TestController {

    @RequestMapping("test")
    public int test(int i){
        System.out.println(i);
        return i;
    }
}
