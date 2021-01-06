package com.ygl.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.bean.PmsSkuInfo;
import com.ygl.gmall.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ygl
 * @description
 * @date 2021/1/6 10:27
 */
@RestController
@CrossOrigin
public class SkuController {

    @Reference
    SkuService skuService;

    /**
     * @author ygl
     * sku保存功能
     * @date 2021-01-06 10:28
     */
    @PostMapping("saveSkuInfo")
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo) {
        //productId 没有封装进去，接下来我们采用自己封装
        pmsSkuInfo.setProductId(pmsSkuInfo.getSpuId());
        //处理默认图片（如果用户没有选择默认图片，那么我们就将第一张图片当作默认图片）
        String skuDefaultImg = pmsSkuInfo.getSkuDefaultImg();
        if (StringUtils.isBlank(skuDefaultImg)) {
            String imgUrl = pmsSkuInfo.getSkuImageList().get(0).getImgUrl();
            pmsSkuInfo.setSkuDefaultImg(imgUrl);
        }

        int i = skuService.saveSkuInfo(pmsSkuInfo);
        return "success";
    }

}
