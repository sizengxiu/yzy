package com.yzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Hello world!
 */
@SpringBootApplication
@MapperScan("com.yzy.dao")
@EnableTransactionManagement
public class FileScanServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileScanServerApplication.class,args);
    }
}

