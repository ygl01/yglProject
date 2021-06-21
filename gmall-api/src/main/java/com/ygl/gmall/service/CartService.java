package com.ygl.gmall.service;

import java.util.List;

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

    List<OmsCartItem> cartList(String userId, String skuId);

    void checkCart(String skuId,String memberId, String isChecked);

}
