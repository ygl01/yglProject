package com.ygl.gmall.item2.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.Module;
import com.ygl.gmall.bean.*;
import com.ygl.gmall.service.AttrService;
import com.ygl.gmall.service.SkuService;
import com.ygl.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2021/1/6 16:01
 */
@Controller
public class ItemController {

    @Reference
    SkuService skuService;

    @Reference
    SpuService spuService;

    /**
     * @author ygl
     * 测试方法
     * @date 2021-01-06 20:08
    */
    @RequestMapping("index")
    public String index(ModelMap modelMap) {

        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("循环数据："+i);
        }
        modelMap.put("list",list);
        modelMap.put("hello", "hello thymeleaf!");
        modelMap.put("check","1");
        return "index";
    }

    /**
     * @author ygl
     * 根据skuid进行查询单个具体商品
     * @date 2021-01-07 14:12
    */
    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId,ModelMap modelMap){

        
        PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId);
        //sku对象
        modelMap.put("skuInfo",pmsSkuInfo);
        //sku销售属性列表
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(),skuId);//传的商品id
        modelMap.put("spuSaleAttrListCheckBySku",pmsProductSaleAttrs);

        return "item";
    }
}
