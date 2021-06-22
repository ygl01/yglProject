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
 * @since 2021-05-30 19:28
 **/
@EnableDubbo
@SpringBootApplication
public class GmallCartWebApplication {

    public static void main(String[] args) {

        SpringApplication.run(GmallCartWebApplication.class, args);
    }

}
