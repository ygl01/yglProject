// Copyright (C) 2021 Focus Media Holding Ltd. All Rights Reserved.

package com.ygl.gmall.cart.service.impl;

import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ygl.gmall.bean.OmsCartItem;
import com.ygl.gmall.cart.mapper.OmsCartItemMapper;
import com.ygl.gmall.service.CartService;
import com.ygl.gmall.util.RedisUtil;

/**
 * TODO
 *
 * @author ygl
 * @version V1.0
 * @since 2021-06-14 17:26
 **/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    OmsCartItemMapper omsCartItemMapper;

    @Override
    public OmsCartItem ifCartExistByUser(String memberId, String skuId) {

        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        omsCartItem.setProductSkuId(skuId);
        OmsCartItem cartItem = omsCartItemMapper.selectOne(omsCartItem);
        return cartItem;
    }

    @Override
    public void addCart(OmsCartItem omsCartItemFromDb) {

        if (StringUtils.isNotBlank(omsCartItemFromDb.getMemberId())) {
            omsCartItemMapper.insert(omsCartItemFromDb);
        }

    }

    @Override
    public void updateCart(OmsCartItem omsCartItemFromDb) {

        if (StringUtils.isNotBlank(omsCartItemFromDb.getMemberId())) {
            Example example = new Example(OmsCartItem.class);
            example.createCriteria().andEqualTo("id", omsCartItemFromDb.getId());
            omsCartItemMapper.updateByExampleSelective(omsCartItemFromDb, example);
        }
    }

    @Override
    public void flushCartCash(String memberId) {

        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        List<OmsCartItem> omsCartItems = omsCartItemMapper.select(omsCartItem);

        //同步到缓存中
        Jedis jedis = redisUtil.getJedis();
        //将查询到omsCartItem集合转换为Map集合
        Map<String, String> map = new HashMap<>();
        for (OmsCartItem cartItem : omsCartItems) {
            map.put(cartItem.getProductSkuId(), JSON.toJSONString(cartItem));
        }
        //将查询到所有OmsCartItem放进redis数据库中
        jedis.hmset("user:" + memberId + ":cart", map);
        jedis.close();
    }

}
