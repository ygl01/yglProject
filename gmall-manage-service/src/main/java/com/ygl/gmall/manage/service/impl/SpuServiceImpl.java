package com.ygl.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ygl.gmall.bean.PmsProductInfo;
import com.ygl.gmall.manage.mapper.SpuMapper;
import com.ygl.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/26 12:09
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    SpuMapper spuMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        List<PmsProductInfo> pmsProductInfos = spuMapper.select(pmsProductInfo);
        return pmsProductInfos;
    }
}
