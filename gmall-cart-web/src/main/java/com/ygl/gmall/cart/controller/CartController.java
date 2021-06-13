// Copyright (C) 2021 Focus Media Holding Ltd. All Rights Reserved.

package com.ygl.gmall.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.service.SkuService;

/**
 * TODO
 * 购物车controller层
 *
 * @author ygl
 * @version V1.0
 * @since 2021-05-30 19:04
 **/
@Controller
public class CartController {

    @Reference
    SkuService skuService;

    /**
     * TODO 重定向新页面，因为需要将内容写在新页面
     *
     * @return null
     * @author ygl
     * @date 2021/5/30 19:06
     **/
    @RequestMapping("addToCart")
    public String addToCart() {


        return "redirect:/success.html";
    }

}
