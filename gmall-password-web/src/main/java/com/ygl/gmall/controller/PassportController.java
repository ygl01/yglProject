package com.ygl.gmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 认证中心页面
 */
@Controller
@CrossOrigin
public class PassportController {
    @GetMapping("index")
    public String index(){
        System.out.println("进来了小老弟。");
        return "index";
    }
}
