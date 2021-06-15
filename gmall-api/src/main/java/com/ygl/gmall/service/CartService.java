package com.ygl.gmall.service;

import com.ygl.gmall.bean.OmsCartItem;

/**
 * @author yangaoling
 * @version 1.0
 * @date 2021/6/14 17:23
 */
public interface CartService {

    OmsCartItem ifCartExistByUser(String memberId, String skuId);

    void addCart(OmsCartItem omsCartItemFromDb);

    void updateCart(OmsCartItem omsCartItemFromDb);

    void flushCartCash(String memberId);

}
