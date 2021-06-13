package com.ygl.gmall.manage.mapper;

import com.ygl.gmall.bean.PmsSkuInfo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2021/1/6 10:37
 */
@Mapper
public interface SkuInfoMapper extends tk.mybatis.mapper.common.Mapper<PmsSkuInfo> {

    List<PmsSkuInfo> selectSkuSaleAttrValueListBySpu(String productId);

}
