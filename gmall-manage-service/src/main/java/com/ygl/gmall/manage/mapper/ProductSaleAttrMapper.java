package com.ygl.gmall.manage.mapper;

import com.ygl.gmall.bean.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/26 18:17
 */
@Mapper
public interface ProductSaleAttrMapper extends tk.mybatis.mapper.common.Mapper<PmsProductSaleAttr> {
    List<PmsProductSaleAttr> selectSpuSaleAttrListCheckBySku(@Param("productId") String productId, @Param("skuId") String skuId);
}
