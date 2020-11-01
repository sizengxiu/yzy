package com.yzy;

import com.yzy.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Hello world!
 */


@SpringBootApplication
@Slf4j
public class FileScanClientApp {
    public static void main(String[] args) {
        SpringApplication.run(FileScanClientApp.class, args);

    }




}
