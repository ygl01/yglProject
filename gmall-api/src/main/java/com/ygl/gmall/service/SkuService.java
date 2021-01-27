package com.ygl.gmall.service;

import com.ygl.gmall.bean.PmsSkuInfo;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2021/1/6 10:34
 */

public interface SkuService {
    int saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuById(String skuId,String remoteAddr);

    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId);
}
