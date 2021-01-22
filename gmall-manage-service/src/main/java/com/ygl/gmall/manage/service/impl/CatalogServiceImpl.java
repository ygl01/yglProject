package com.ygl.gmall.manage.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ygl.gmall.bean.PmsBaseCatalog1;
import com.ygl.gmall.bean.PmsBaseCatalog2;
import com.ygl.gmall.bean.PmsBaseCatalog3;
import com.ygl.gmall.manage.mapper.Catalog1Mapper;
import com.ygl.gmall.manage.mapper.Catalog2Mapper;
import com.ygl.gmall.manage.mapper.Catalog3Mapper;
import com.ygl.gmall.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/24 20:45
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    Catalog1Mapper catalog1Mapper;
    @Autowired
    Catalog2Mapper catalog2Mapper;
    @Autowired
    Catalog3Mapper catalog3Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        System.out.println("俺又来喽、、、" );
        List<PmsBaseCatalog1> pmsBaseCatalog1s = catalog1Mapper.selectAll();
        System.out.println("让我看看打印的什么："+pmsBaseCatalog1s);
        return pmsBaseCatalog1s;
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        PmsBaseCatalog2 pmsBaseCatalog2 = new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(catalog1Id);
        List<PmsBaseCatalog2> baseCatalog2s = catalog2Mapper.select(pmsBaseCatalog2);
        return baseCatalog2s;
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        PmsBaseCatalog3 pmsBaseCatalog3 = new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2Id(catalog2Id);
        List<PmsBaseCatalog3> catalog3s = catalog3Mapper.select(pmsBaseCatalog3);
        return catalog3s;
    }
}
