package com.ygl.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ygl.gmall.bean.*;
import com.ygl.gmall.manage.mapper.*;
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
    SpuInfoMapper spuMapperInfo;

    @Autowired
    BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    ProductSaleAttrMapper productSaleAttrMapper;

    @Autowired
    ProductImageMapper productImageMapper;

    @Autowired
    ProductSaleAttrValueMapper productSaleAttrValueMapper;


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
    public int saveSpuInfo(PmsProductInfo pmsProductInfo1) {

        int insert = spuMapperInfo.insert(pmsProductInfo1);
        //获取插入数据库后的商品id
        PmsProductInfo pmsProductInfo = spuMapperInfo.selectOne(pmsProductInfo1);
        String id = pmsProductInfo.getId();

        System.out.println("打印ID：" + pmsProductInfo.getId());
        List<PmsProductImage> spuImageList = pmsProductInfo1.getSpuImageList();
        List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo1.getSpuSaleAttrList();
        //存储ImageList
        for (PmsProductImage pmsProductImage : spuImageList) {
            pmsProductImage.setProductId(id);
            //暂时未写
            productImageMapper.insert(pmsProductImage);
        }

        //存储SaleAttrList
        for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrList) {
            pmsProductSaleAttr.setProductId(id);
            productSaleAttrMapper.insert(pmsProductSaleAttr);
            List<PmsProductSaleAttrValue> spuSaleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : spuSaleAttrValueList) {
                pmsProductSaleAttrValue.setProductId(id);
                productSaleAttrValueMapper.insert(pmsProductSaleAttrValue);
            }
        }
        return insert;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {

        PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = productSaleAttrMapper.select(pmsProductSaleAttr);
        for (PmsProductSaleAttr productSaleAttr : pmsProductSaleAttrs) {
            PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
            pmsProductSaleAttrValue.setProductId(productSaleAttr.getProductId());
            pmsProductSaleAttrValue.setSaleAttrId(productSaleAttr.getSaleAttrId());
            List<PmsProductSaleAttrValue> select = productSaleAttrValueMapper.select(pmsProductSaleAttrValue);
            productSaleAttr.setSpuSaleAttrValueList(select);
        }

        return pmsProductSaleAttrs;
    }

    @Override
    public List<PmsProductImage> spuImageList(String spuId) {

        PmsProductImage pmsProductImage = new PmsProductImage();
        pmsProductImage.setProductId(spuId);
        List<PmsProductImage> select = productImageMapper.select(pmsProductImage);
        return select;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId, String skuId) {
//        PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
//        pmsProductSaleAttr.setProductId(productId);
//        List<PmsProductSaleAttr> pmsProductSaleAttrs = productSaleAttrMapper.select(pmsProductSaleAttr);
//        for (PmsProductSaleAttr productSaleAttr : pmsProductSaleAttrs) {
//            String saleAttrId = productSaleAttr.getSaleAttrId();
//            PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
//            pmsProductSaleAttrValue.setSaleAttrId(saleAttrId);
//            pmsProductSaleAttrValue.setProductId(productId);
//            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = productSaleAttrValueMapper.select(pmsProductSaleAttrValue);
//            productSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
//
//        }
        List<PmsProductSaleAttr> pmsProductSaleAttrs =
                productSaleAttrMapper.selectSpuSaleAttrListCheckBySku(productId, skuId);
        return pmsProductSaleAttrs;
    }

}
