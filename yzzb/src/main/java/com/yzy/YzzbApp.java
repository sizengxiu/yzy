package com.yzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.yzy.dao")
@EnableTransactionManagement
public class YzzbApp {
    public static void main(String[] args) {
        SpringApplication.run(YzzbApp.class);
    }
}
