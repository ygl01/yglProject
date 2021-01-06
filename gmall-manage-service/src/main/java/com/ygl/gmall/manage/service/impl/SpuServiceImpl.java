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

        System.out.println("打印ID："+pmsProductInfo.getId());
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
}
