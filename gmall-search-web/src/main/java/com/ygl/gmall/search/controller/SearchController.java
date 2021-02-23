package com.ygl.gmall.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class SearchController {

    @GetMapping("list.html")
    public String list(String catalog3Id){
        System.out.println("进来了、、、");
        return "index";
    }

    @GetMapping("index")
    public String index(){
        return "/index";
    }
}
