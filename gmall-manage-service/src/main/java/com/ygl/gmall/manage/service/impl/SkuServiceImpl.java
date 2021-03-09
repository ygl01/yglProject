package com.ygl.gmall.manage.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ygl.gmall.bean.PmsSkuAttrValue;
import com.ygl.gmall.bean.PmsSkuImage;
import com.ygl.gmall.bean.PmsSkuInfo;
import com.ygl.gmall.bean.PmsSkuSaleAttrValue;
import com.ygl.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.ygl.gmall.manage.mapper.PmsSkuImageMapper;
import com.ygl.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.ygl.gmall.manage.mapper.SkuInfoMapper;
import com.ygl.gmall.service.SkuService;
import com.ygl.gmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    RedisUtil redisUtil;

    @Override
    public int saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        //插入skuInfo
        int insert = skuInfoMapper.insertSelective(pmsSkuInfo);
        List<PmsSkuInfo> select = skuInfoMapper.select(pmsSkuInfo);
        String id = select.get(0).getId();
        String id1 = pmsSkuInfo.getId();
        System.out.println("打印skuInfo查询的id:" + id);
        System.out.println("打印skuInfo获取的id1:" + id1);
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

    /**
     * 从数据库中进行查询商品
     *
     * @param skuId
     * @return
     */
    public PmsSkuInfo getSkuByIdFromDb(String skuId) {
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
        if (pmsSkuImages != null && pmsSkuAttrValues != null && pmsSkuSaleAttrValues != null && pmsSkuInfo1 != null) {
            pmsSkuInfo1.setSkuImageList(pmsSkuImages);
            pmsSkuInfo1.setSkuAttrValueList(pmsSkuAttrValues);
            pmsSkuInfo1.setSkuSaleAttrValueList(pmsSkuSaleAttrValues);
        }
        System.out.println("打印：" + pmsSkuInfo1);
        return pmsSkuInfo1;
    }

    /**
     * 从redis中进行查询
     *
     * @param skuId
     * @return
     */
    @Override
    public PmsSkuInfo getSkuById(String skuId, String ip) {
        System.out.println("ip为"+ip+"的同学："+Thread.currentThread().getName()+"进入商品详情的请求！");
        long start = new Date().getTime();
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        System.out.println("进入缓存的redis");
        //连接缓存
        Jedis jedis = redisUtil.getJedis();
        //查询缓存
        String skuKey = "sku:" + skuId + ":info";
        String skuJson = jedis.get(skuKey);
        if (!StringUtils.isBlank(skuJson)) {//if (skuJson!= null&&skuJson.equals(""))
            System.out.println("ip为"+ip+"的同学："+Thread.currentThread().getName()+"从缓存中查询到了商品详情！");
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        } else {
            //如果缓存中没有，去mysql中查询
            System.out.println("ip为"+ip+"的同学："+Thread.currentThread().getName()+"发现缓存中没有，申请缓存的分布式锁："+"sku:" + skuId + ":lock");
            //设置分布式锁
            String token = UUID.randomUUID().toString();
            String OK = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10000);
            if ((!StringUtils.isBlank(OK)) && OK.equals("OK")) {
                System.out.println("ip为"+ip+"的同学："+Thread.currentThread().getName()+"成功拿到锁，有权在10s内访问数据库："+"sku:" + skuId + ":lock");
                //分布式锁设置成功，有权利在10秒内访问数据库
                pmsSkuInfo = getSkuByIdFromDb(skuId);
                try {
                    Thread.sleep(1000*5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //mysql查询结果存放在redis缓存中
                if (pmsSkuInfo != null) {
                    String s = JSON.toJSONString(pmsSkuInfo);
                    jedis.set("sku:" + skuId + ":info", s);
                } else {
                    //如果该sku不存在
                    //为了防止缓存穿透，设置一个短暂的key的skuId过期,值为空
                    jedis.setex("sku:" + skuId + ":info", 60, "");
                }

                //在访问mysql后，要将mysql的分布式锁进行释放掉
                System.out.println("ip为"+ip+"的同学："+Thread.currentThread().getName()+"使用完毕，将锁归还："+"sku:" + skuId + ":lock");
                String lockToken = jedis.get("sku:" + skuId + ":lock");
                if (!(StringUtils.isBlank(lockToken))&&lockToken.equals(token)){

                    jedis.del("sku:" + skuId + ":lock");//用token（也就是value和key两个一起判断）删除是否是自己的锁
                }

            } else {
                System.out.println("ip为"+ip+"的同学："+Thread.currentThread().getName()+"没有拿到锁，开始自旋："+"sku:" + skuId + ":lock");
                //设置失败，开启自旋（线程睡眠几秒之后，重新尝试访问该方法）
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return getSkuById(skuId,ip);
            }
        }

        //关闭缓存
        jedis.close();
        long end = new Date().getTime();
        long t = end - start;
        System.out.println("用时：" + t);
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        List<PmsSkuInfo> pmsSkuInfos = skuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
        return pmsSkuInfos;
    }

    @Override
    public List<PmsSkuInfo> getAllSkuInfo() {
        System.out.println("来喽1");
        List<PmsSkuInfo> pmsSkuInfos = skuInfoMapper.selectAll();
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            String skuId = pmsSkuInfo.getId();
            PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(skuId);
            List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);
            pmsSkuInfo.setSkuAttrValueList(pmsSkuAttrValues);
        }
        System.out.println("走喽1："+pmsSkuInfos.size());
        return pmsSkuInfos;
    }
}
