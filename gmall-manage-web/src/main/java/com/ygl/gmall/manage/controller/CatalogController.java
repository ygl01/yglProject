package com.ygl.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.bean.PmsBaseCatalog1;
import com.ygl.gmall.bean.PmsBaseCatalog2;
import com.ygl.gmall.bean.PmsBaseCatalog3;
import com.ygl.gmall.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/24 20:14
 */
@CrossOrigin  //请求跨域注解
@Controller
public class CatalogController {

    @Reference
    CatalogService catalogService;


    @PostMapping("getCatalog1")
    @ResponseBody
    public List<PmsBaseCatalog1> getCatalog1() {
        List<PmsBaseCatalog1> pmsBaseCatalog1s = catalogService.getCatalog1();
        System.out.println("打印：" + pmsBaseCatalog1s);
        return pmsBaseCatalog1s;
    }

    @PostMapping("getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        List<PmsBaseCatalog2> baseCatalog2s = catalogService.getCatalog2(catalog1Id);
        return baseCatalog2s;
    }

    @PostMapping("getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        List<PmsBaseCatalog3> baseCatalog3s = catalogService.getCatalog3(catalog2Id);
        return baseCatalog3s;
    }
}
