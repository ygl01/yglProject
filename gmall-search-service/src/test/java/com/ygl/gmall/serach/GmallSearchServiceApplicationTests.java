package com.ygl.gmall.serach;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.bean.PmsSearchSkuInfo;
import com.ygl.gmall.bean.PmsSkuInfo;
import com.ygl.gmall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallSearchServiceApplicationTests {
    @Reference
    SkuService skuService;

    @Autowired
    JestClient jestClient;

    @Test
    public void contextLoads() throws IOException {
        /*
        *查询es的json
             GET gmall/PmsSkuInfo/_search
            {
              "query": {
                "bool": {
                  "filter": [
                    {"terms":{"skuAttrValueList.valueId":["39","40","45"]}},
                    {
                      "term": {
                        "skuAttrValueList.valueId": "39"
                      }
                    },{
                      "term": {
                        "skuAttrValueList.valueId": "43"
                      }
                    }
                  ],
                  "must": [
                    {
                      "match": {
                        "skuName": "尊享陶瓷"
                        }
                    }
                  ]
                }
              }
            }
        * */
        //jest的dsl工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //filter
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId","39");
        TermQueryBuilder termQueryBuilder1 = new TermQueryBuilder("skuAttrValueList.valueId","43");
        boolQueryBuilder.filter(termQueryBuilder);
        boolQueryBuilder.filter(termQueryBuilder1);
//        terms的用法
//        TermsQueryBuilder termsQueryBuilder = new TermsQueryBuilder("","");
//        boolQueryBuilder.filter(termsQueryBuilder);
        //must
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName","尊享陶瓷");
        boolQueryBuilder.must(matchQueryBuilder);
        //query
        searchSourceBuilder.query(boolQueryBuilder);


        //from
        searchSourceBuilder.from(0);
        //size
//        searchSourceBuilder.size(20);
        //highlight
        searchSourceBuilder.highlight(null);

        String dslStr = searchSourceBuilder.toString();
        System.out.println("打印语句："+dslStr);
        //用api执行复杂查询
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = new ArrayList<>();
        Search search = new Search.Builder(dslStr).addIndex("gmall").addType("PmsSkuInfo").build();
        SearchResult execute = jestClient.execute(search);
        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);
        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
            PmsSearchSkuInfo source = hit.source;
            pmsSearchSkuInfoList.add(source);
        }
        System.out.println("查询结果大小："+pmsSearchSkuInfoList.size());
    }
    @Test
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
        System.out.println("数据库查询出来大小："+allSkuInfo.size());
        //转化为es数据结构
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = new ArrayList<>();
        for (PmsSkuInfo pmsSkuInfo : allSkuInfo) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
            //利用工具类进行将一个实体复制到另一个实体中
            BeanUtils.copyProperties(pmsSkuInfo,pmsSearchSkuInfo);
            pmsSearchSkuInfo.setId(Long.parseLong(pmsSkuInfo.getId()));
            pmsSearchSkuInfoList.add(pmsSearchSkuInfo);

        }
        //导入es
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfoList) {
            Index put = new Index.Builder(pmsSearchSkuInfo).index("gmall").type("PmsSkuInfo").id(pmsSearchSkuInfo.getId()+"").build();
            jestClient.execute(put);
        }
    }
}
