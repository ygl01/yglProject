package com.ygl.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.bean.PmsProductInfo;
import com.ygl.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/26 11:35
 */
@Controller
@CrossOrigin
public class SpuController {
    @Reference
    SpuService spuService;

    @GetMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id){
        List<PmsProductInfo> spuLists = spuService.spuList(catalog3Id);
        return spuLists;
    }


}
