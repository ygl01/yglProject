// Copyright (C) 2021 Focus Media Holding Ltd. All Rights Reserved.

package com.ygl.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

/**
 * TODO
 *
 * @author ygl
 * @version V1.0
 * @since 2021-06-21 18:21
 **/
@EnableDubbo
@SpringBootApplication
public class GmallPassPortWebApplication {

    public static void main(String[] args) {

        SpringApplication.run(GmallPassPortWebApplication.class, args);
    }

}
