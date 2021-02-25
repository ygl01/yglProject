package com.ygl.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.bean.PmsSearchParam;
import com.ygl.gmall.bean.PmsSearchSkuInfo;
import com.ygl.gmall.service.SearchService;
import com.ygl.gmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@CrossOrigin
public class SearchController {

    @Reference
    SearchService searchService;

    @GetMapping("list.html")
    public String list(PmsSearchParam pmsSearchParam, ModelMap modelMap) throws IOException {

        //调用搜索服务，返回搜索服务结果
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = searchService.list(pmsSearchParam);
        System.out.println("查询结果大小："+pmsSearchSkuInfoList.size());
        modelMap.put("skuLsInfoList",pmsSearchSkuInfoList);
        return "list";
    }

    @GetMapping("index")
    public String index(){
        return "/index";
    }
}
