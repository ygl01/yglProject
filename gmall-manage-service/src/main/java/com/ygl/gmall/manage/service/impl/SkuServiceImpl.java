package com.ygl.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ygl.gmall.bean.PmsSkuAttrValue;
import com.ygl.gmall.bean.PmsSkuImage;
import com.ygl.gmall.bean.PmsSkuInfo;
import com.ygl.gmall.bean.PmsSkuSaleAttrValue;
import com.ygl.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.ygl.gmall.manage.mapper.PmsSkuImageMapper;
import com.ygl.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.ygl.gmall.manage.mapper.SkuInfoMapper;
import com.ygl.gmall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2021/1/6 10:36
 */
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Override
    public int saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        //插入skuInfo
        int insert = skuInfoMapper.insertSelective(pmsSkuInfo);
        List<PmsSkuInfo> select = skuInfoMapper.select(pmsSkuInfo);
        String id = select.get(0).getId();
        String id1 = pmsSkuInfo.getId();
        System.out.println("打印skuInfo查询的id:"+id);
        System.out.println("打印skuInfo获取的id1:"+id1);
        //插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(id);
            int insert1 = pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }
        //插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(id);
            int insert1 = pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }

        //插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(id);
            //将SpuImgId赋值给ProductImgId
            pmsSkuImage.setProductImgId(pmsSkuImage.getSpuImgId());
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }
        return insert;
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo pmsSkuInfo1 = skuInfoMapper.selectOne(pmsSkuInfo);
        //查询skuImageList
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImage);
        //查询skuAttrValueList
        PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
        pmsSkuAttrValue.setSkuId(skuId);
        List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);
        //查询skuSaleAttrValueList
        PmsSkuSaleAttrValue pmsSkuSaleAttrValue = new PmsSkuSaleAttrValue();
        pmsSkuSaleAttrValue.setSkuId(skuId);
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = pmsSkuSaleAttrValueMapper.select(pmsSkuSaleAttrValue);
        pmsSkuInfo1.setSkuImageList(pmsSkuImages);
        pmsSkuInfo1.setSkuAttrValueList(pmsSkuAttrValues);
        pmsSkuInfo1.setSkuSaleAttrValueList(pmsSkuSaleAttrValues);
        return pmsSkuInfo1;
    }
}
