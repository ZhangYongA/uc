package com.y.uc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by zhangyong on 2017/7/28.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.y.uc")
public class UcApplication {

    public static void main(String[] args) {
        SpringApplication.run(UcApplication.class, args);
    }

}
