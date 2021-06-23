// Copyright (C) 2021 Focus Media Holding Ltd. All Rights Reserved.

package com.ygl.gmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO
 * passportController
 *
 * @author ygl
 * @version V1.0
 * @since 2021-06-21 18:59
 **/
@Controller
public class PassportController {

    @RequestMapping("index")
    public String index() {

        return "index";
    }
    /**
    *
    * TODO
    * login接口
    * @return null
    * @author ygl
    * @date 2021/6/22 18:07
    **/
    @RequestMapping("login")
    public String login() {

        return "index";
    }

}
