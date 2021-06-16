// Copyright (C) 2021 Focus Media Holding Ltd. All Rights Reserved.

package com.ygl.gmall;

import tk.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO
 *
 * @author ygl
 * @version V1.0
 * @since 2021-06-14 17:28
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.ygl.gmall.cart.mapper")
public class GmallCartServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(GmallCartServiceApplication.class, args);
    }

}
