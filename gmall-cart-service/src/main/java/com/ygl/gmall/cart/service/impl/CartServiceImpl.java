// Copyright (C) 2021 Focus Media Holding Ltd. All Rights Reserved.

package com.ygl.gmall.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ygl.gmall.bean.OmsCartItem;
import com.ygl.gmall.service.CartService;

/**
 * TODO
 *
 * @author ygl
 * @version V1.0
 * @since 2021-06-14 17:26
 **/
@Service
public class CartServiceImpl implements CartService {

    @Override
    public OmsCartItem ifCartExistByUser(String memberId, String skuId) {

        return null;
    }

    @Override
    public void addCart(OmsCartItem omsCartItemFromDb) {

    }

    @Override
    public void updateCart(OmsCartItem omsCartItemFromDb) {

    }

    @Override
    public void flushCartCash(String memberId) {

    }

}
