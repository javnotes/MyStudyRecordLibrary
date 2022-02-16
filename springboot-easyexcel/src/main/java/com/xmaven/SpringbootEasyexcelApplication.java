package com.xmaven;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.xmaven.mapper")
public class SpringbootEasyexcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEasyexcelApplication.class, args);
    }

}
