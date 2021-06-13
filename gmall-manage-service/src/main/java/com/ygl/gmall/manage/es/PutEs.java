package com.ygl.gmall.manage.es;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.bean.PmsSearchSkuInfo;
import com.ygl.gmall.bean.PmsSkuInfo;
import com.ygl.gmall.service.SkuService;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PutEs {

    @Autowired
    SkuService skuService;

    @Autowired
    JestClient jestClient;

    @GetMapping("/putEs")
    public void put() throws IOException {
        /**
         * 创建es的数据结构
         * PUT gmall
         * {
         *   "mappings": {
         *     "PmsSkuInfo":{
         *       "properties": {
         *         "id":{
         *           "type": "keyword",
         *           "index": true
         *         },
         *         "skuName":{
         *           "type": "text",
         *           "analyzer": "ik_max_word"
         *         },
         *         "skuDesc":{
         *           "type": "text",
         *           "analyzer": "ik_smart"
         *         },
         *         "catalog3Id":{
         *           "type": "keyword"
         *         },
         *         "price":{
         *           "type": "double"
         *         },
         *         "skuDefaultImg":{
         *           "type": "keyword",
         *           "index": false
         *         },
         *         "hotScore":{
         *           "type": "double"
         *         },
         *         "productId":{
         *           "type": "keyword"
         *         },
         *         "skuAttrValueList":{
         *           "properties": {
         *             "attrId":{
         *               "type": "keyword"
         *             },
         *             "valueId":{
         *                "type": "keyword"
         *             }
         *           }
         *         }
         *       }
         *     }
         *   }
         * }
         */
        //查询mysql数据
        List<PmsSkuInfo> allSkuInfo = skuService.getAllSkuInfo();
        System.out.println("数据库查询出来大小：" + allSkuInfo.size());
        //转化为es数据结构
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = new ArrayList<>();
        for (PmsSkuInfo pmsSkuInfo : allSkuInfo) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
            //利用工具类进行将一个实体复制到另一个实体中
            BeanUtils.copyProperties(pmsSkuInfo, pmsSearchSkuInfo);
            pmsSearchSkuInfo.setId(Long.parseLong(pmsSkuInfo.getId()));
            pmsSearchSkuInfoList.add(pmsSearchSkuInfo);

        }
        //导入es
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfoList) {
            Index put = new Index.Builder(pmsSearchSkuInfo).index("gmall").type("PmsSkuInfo")
                    .id(pmsSearchSkuInfo.getId() + "").build();
            jestClient.execute(put);
        }
    }

}
