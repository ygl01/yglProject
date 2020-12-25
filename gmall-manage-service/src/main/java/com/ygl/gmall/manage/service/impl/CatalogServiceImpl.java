package com.ygl.gmall.manage.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ygl.gmall.bean.PmsBaseCatalog1;
import com.ygl.gmall.manage.mapper.CatalogMapper;
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
    CatalogMapper catalogMapper;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        List<PmsBaseCatalog1> pmsBaseCatalog1s = catalogMapper.selectAll();
        return pmsBaseCatalog1s;
    }
}
