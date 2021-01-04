package com.ygl.gmall.service;

import com.ygl.gmall.bean.PmsBaseSaleAttr;
import com.ygl.gmall.bean.PmsProductInfo;

import java.util.List;

/**
 * @author ygl
 * @description 测试下
 * @date 2020/12/26 12:08
 */
public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);
    List<PmsBaseSaleAttr> baseSaleAttrList();
    int saveSpuInfo(PmsProductInfo pmsProductInfo);
}
