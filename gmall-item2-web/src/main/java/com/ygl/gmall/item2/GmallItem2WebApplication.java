package com.ygl.gmall.item2;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ygl
 * @description
 * @date 2021/1/6 15:49
 */
@EnableDubbo
@SpringBootApplication
public class GmallItem2WebApplication {

    public static void main(String[] args) {

        SpringApplication.run(GmallItem2WebApplication.class, args);
    }

}
