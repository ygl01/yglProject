package com.ygl.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.bean.*;
import com.ygl.gmall.service.AttrService;
import com.ygl.gmall.service.SearchService;
import com.ygl.gmall.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
        System.out.println("查询结果大小：" + pmsSearchSkuInfoList.size());
        modelMap.put("skuLsInfoList", pmsSearchSkuInfoList);

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
            System.out.println("查询的平台属性：" + pmsBaseAttrInfo.getAttrName());
        }
        modelMap.put("attrList", pmsBaseAttrInfos);

        //对平台属性集合进一步处理，去掉当前条件中valueId的属性组
        String[] delValueId = pmsSearchParam.getValueId();
        if (delValueId != null){
            Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();
            while (iterator.hasNext()){
                PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                    String id = pmsBaseAttrValue.getId();
                    for (String s : delValueId) {
                        if (s.equals(id)){
                            //删除该属性所在的属性组
                            iterator.remove();
                        }
                    }
                }
            }
        }


        String urlParam = getUrlParam(pmsSearchParam);
        modelMap.put("urlParam", urlParam);

        return "list";
    }

    private String getUrlParam(PmsSearchParam pmsSearchParam) {
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String[] skuAttrValueList = pmsSearchParam.getValueId();
        String urlParam = "";

        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)){
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }

        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)){
                urlParam = urlParam +"&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }

        if (skuAttrValueList != null) {
            for (String pmsSkuAttrValue : skuAttrValueList) {

                urlParam = urlParam + "&valueId=" + pmsSkuAttrValue;
            }
        }

        System.out.println("打印urlParam："+urlParam);
        return urlParam;
    }

    @GetMapping("index")
    public String index() {
        return "/index";
    }
}
