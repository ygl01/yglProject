package com.ygl.gmall.item2.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.Module;
import com.ygl.gmall.bean.*;
import com.ygl.gmall.service.AttrService;
import com.ygl.gmall.service.SkuService;
import com.ygl.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            list.add("循环数据：" + i);
        }
        modelMap.put("list", list);
        modelMap.put("hello", "hello thymeleaf!");
        modelMap.put("check", "1");
        return "index";
    }

    /**
     * @author ygl
     * 根据skuid进行查询单个具体商品
     * @date 2021-01-07 14:12
     */
    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap modelMap, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();//获取URL地址

//        request.getHeader("")//如果采用negix代理，则使用此方法
        System.out.println("进入redis");
        PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId, remoteAddr);
        System.out.println("出来redis!");
        //sku对象
        modelMap.put("skuInfo", pmsSkuInfo);
        //sku销售属性列表
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(), skuId);//传的商品id
        modelMap.put("spuSaleAttrListCheckBySku", pmsProductSaleAttrs);

        //查询sku的spu的其他sku的集合hash表
        List<PmsSkuInfo> pmsSkuInfos = skuService.getSkuSaleAttrValueListBySpu(pmsSkuInfo.getProductId());
        Map<String, String> skuSaleAttrHash = new HashMap<>();
        for (PmsSkuInfo skuInfo : pmsSkuInfos) {
            String k = "";
            String v = skuInfo.getId();//获得sku的id
            List<PmsSkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
                k += pmsSkuSaleAttrValue.getSaleAttrValueId() + "|";//"239|245"

            }
            skuSaleAttrHash.put(k, v);
        }
        //将sku的销售属性hash表放到页面
        String skuSaleAttrHashJsonStr = JSON.toJSONString(skuSaleAttrHash);
        modelMap.put("skuSaleAttrHashJsonStr", skuSaleAttrHashJsonStr);

        return "item";
    }
}
