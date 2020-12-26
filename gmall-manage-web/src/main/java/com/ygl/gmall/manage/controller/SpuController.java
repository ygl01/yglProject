package com.ygl.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.bean.PmsBaseSaleAttr;
import com.ygl.gmall.bean.PmsProductInfo;
import com.ygl.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * @author ygl
     * 查询spu商品列表
     * @date 2020-12-26 17:36
     */
    @GetMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id) {
        List<PmsProductInfo> spuLists = spuService.spuList(catalog3Id);
        return spuLists;
    }

    /**
     * @author ygl
     * 查询销售属性
     * @date 2020-12-26 17:39
     */
    @PostMapping("baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = spuService.baseSaleAttrList();
        return pmsBaseSaleAttrs;
    }

    /**
     * @author ygl
     * 上传图片     就是当点击文件的时候会进行上传
     * @date 2020-12-26 18:41
    */
    @PostMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
        //将图片或者视频上传到分布式的文件存储系统

        //将图片的存储路径返回给前台
        String imgUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1603365312,3218205429&fm=26&gp=0.jpg";
        return imgUrl;
    }
    /**
     * @author ygl
     * 保存商品信息
     * @date 2020-12-26 17:53
     */
    @PostMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {

        int i = spuService.saveSpuInfo(pmsProductInfo);
        return "success。";

    }


}
