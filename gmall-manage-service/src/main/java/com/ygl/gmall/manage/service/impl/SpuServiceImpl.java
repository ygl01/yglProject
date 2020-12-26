package com.ygl.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ygl.gmall.bean.PmsBaseSaleAttr;
import com.ygl.gmall.bean.PmsProductImage;
import com.ygl.gmall.bean.PmsProductInfo;
import com.ygl.gmall.bean.PmsProductSaleAttr;
import com.ygl.gmall.manage.mapper.BaseSaleAttrMapper;
import com.ygl.gmall.manage.mapper.ProductImageMapper;
import com.ygl.gmall.manage.mapper.ProductSaleAttrMapper;
import com.ygl.gmall.manage.mapper.SpuMapperInfo;
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
    SpuMapperInfo spuMapperInfo;

    @Autowired
    BaseSaleAttrMapper baseSaleAttrMapper;
    @Autowired
    ProductSaleAttrMapper productSaleAttrMapper;
    @Autowired
    ProductImageMapper productImageMapper;


    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        List<PmsProductInfo> pmsProductInfos = spuMapperInfo.select(pmsProductInfo);
        return pmsProductInfos;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = baseSaleAttrMapper.selectAll();
        return pmsBaseSaleAttrs;
    }

    @Override
    public int saveSpuInfo(PmsProductInfo pmsProductInfo) {
        List<PmsProductImage> spuImageList = pmsProductInfo.getSpuImageList();
        List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
        //存储ImageList
        for (PmsProductImage pmsProductImage : spuImageList) {
            //暂时未写
        }

        //存储SaleAttrList
        for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrList) {
            productSaleAttrMapper.insert(pmsProductSaleAttr);
        }
        int insert = spuMapperInfo.insert(pmsProductInfo);
        return insert;
    }
}
