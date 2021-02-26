package com.ygl.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.bean.PmsBaseAttrInfo;
import com.ygl.gmall.bean.PmsSearchParam;
import com.ygl.gmall.bean.PmsSearchSkuInfo;
import com.ygl.gmall.bean.PmsSkuAttrValue;
import com.ygl.gmall.service.AttrService;
import com.ygl.gmall.service.SearchService;
import com.ygl.gmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Controller
@CrossOrigin
public class SearchController {

    @Reference
    SearchService searchService;

    @Reference
    AttrService attrService;

    @GetMapping("list.html")
    public String list(PmsSearchParam pmsSearchParam, ModelMap modelMap) throws IOException {

        //调用搜索服务，返回搜索服务结果
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = searchService.list(pmsSearchParam);
        System.out.println("查询结果大小："+pmsSearchSkuInfoList.size());
        modelMap.put("skuLsInfoList",pmsSearchSkuInfoList);

        //抽取检索结果所包含的平台属性集合
        HashSet<String> valueSet = new HashSet<>();
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfoList) {
            List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                String valueId = pmsSkuAttrValue.getValueId();
                valueSet.add(valueId);
            }
        }
        //根据valueId将属性列表查询出来
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.getAttrValueListByValueId(valueSet);
        for (PmsBaseAttrInfo pmsBaseAttrInfo : pmsBaseAttrInfos) {
            System.out.println("查询的平台属性："+pmsBaseAttrInfo.getAttrName());
        }

        modelMap.put("attrList",pmsBaseAttrInfos);
        return "list";
    }

    @GetMapping("index")
    public String index(){
        return "/index";
    }
}
