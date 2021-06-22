// Copyright (C) 2021 Focus Media Holding Ltd. All Rights Reserved.

package com.ygl.gmall.cart.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ygl.gmall.annotations.LoginRequired;
import com.ygl.gmall.bean.OmsCartItem;
import com.ygl.gmall.bean.PmsSkuInfo;
import com.ygl.gmall.service.CartService;
import com.ygl.gmall.service.SkuService;
import com.ygl.gmall.util.CookieUtil;

/**
 * TODO
 * 购物车controller层
 *
 * @author ygl
 * @version V1.0
 * @since 2021-05-30 19:04
 **/
@Controller
public class CartController {

    @Reference
    SkuService skuService;

    @Reference
    CartService cartService;

    /**
     * TODO
     * 去结算页面
     *
     * @return null
     * @author ygl
     * @date 2021/6/22 10:33
     **/
    @RequestMapping("toTrade")
    @LoginRequired
    public String toTrade(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                          ModelMap modelMap) {

        return "toTrade";
    }

    /**
     * TODO 重定向新页面，因为需要将内容写在新页面
     *
     * @return null
     * @author ygl
     * @date 2021/5/30 19:06
     **/
    @RequestMapping("addToCart")
    @LoginRequired(loginSuccess = false)
    //传skuid和quantity(商品数量)
    public String addToCart(String skuId, int num, HttpServletRequest request, HttpServletResponse response) {

        List<OmsCartItem> omsCartItems = new ArrayList<>();
        int quantity = num;
        //调用商品服务查询商品信息
        PmsSkuInfo skuInfo = skuService.getSkuById(skuId, "");
        //将商品信息封装成购物车信息
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCreateDate(new Date()); //购物车商品添加时间
        omsCartItem.setDeleteStatus(0); //购物车删除状态
        omsCartItem.setModifyDate(new Date()); //购物车商品修改时间
        omsCartItem.setPrice(skuInfo.getPrice());
        omsCartItem.setProductAttr(""); //商品属性
        omsCartItem.setProductBrand(""); // 商品商标
        omsCartItem.setProductCategoryId(skuInfo.getCatalog3Id());
        omsCartItem.setProductId(skuInfo.getProductId());
        omsCartItem.setProductName(skuInfo.getSkuName());
        omsCartItem.setProductPic(skuInfo.getSkuDefaultImg());
        omsCartItem.setProductSkuCode("111111111111");
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setQuantity(quantity);

        //判断用户是否登录
        String memberId = ""; //"1"
        if (StringUtils.isBlank(memberId)) {
            //cookie中原有购物车数据
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            //判断cookie是否为空
            if (StringUtils.isBlank(cartListCookie)) {
                omsCartItems.add(omsCartItem);
            } else {
                omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
                //判断添加的购物车数据在cookie中是否存在
                Boolean exist = if_cart_exist(omsCartItems, omsCartItem);
                if (exist) {
                    //之前添加过，更新购物车添加数量
                    for (OmsCartItem cartItem : omsCartItems) {
                        if (cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())) {
                            cartItem.setQuantity(cartItem.getQuantity() + omsCartItem.getQuantity());
                        }
                    }
                } else {
                    //之前没有添加，新增当前购物车
                    omsCartItems.add(omsCartItem);
                }
            }
            //用户没有登录
            //没有登录则走cookie，在cookie中存储数据
            CookieUtil.setCookie(request, response, "cartListCookie", JSON.toJSONString(omsCartItems), 60 * 60 * 72,
                    true);
        } else {
            //用户已经登录
            //登录则走数据库DB也就是走cartService

            //从DB中查出购物车数据
            OmsCartItem omsCartItemFromDb = cartService.ifCartExistByUser(memberId, skuId);
            if (omsCartItemFromDb == null) {
                //DB为空，该用户没有添加商品
                omsCartItem.setMemberId(memberId);
                omsCartItem.setMemberNickname("test小明");
                cartService.addCart(omsCartItem);
            } else {
                //该用户添加过该商品
                //DB 不为空
                omsCartItemFromDb.setQuantity(omsCartItemFromDb.getQuantity() + omsCartItem.getQuantity());
                cartService.updateCart(omsCartItemFromDb);
            }
            //同步缓存
            cartService.flushCartCash(memberId);
        }


        //这里是写的操作，因此这种不是采用转发操作，这里采用的重定向操作：用的redirect
        return "redirect:/success.html";
    }

    /**
     * TODO
     * 购物车列表
     *
     * @return null
     * @author ygl
     * @date 2021/6/20 16:49
     **/
    @RequestMapping("cartList")
    @LoginRequired(loginSuccess = false)
    public String cartList(String skuId, HttpServletRequest request, HttpServletResponse response,
                           ModelMap modelMap) {

        List<OmsCartItem> omsCartItems = new ArrayList<>();
        String memberId = "1";
        if (StringUtils.isNotBlank(memberId)) {
            //代表已经登录  去查询DB
            omsCartItems = cartService.cartList(memberId, skuId);
        } else {
            //代表未登录  直接查询Cookie
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if (StringUtils.isNotBlank(cartListCookie)) {
                omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
            }
        }
        for (OmsCartItem omsCartItem : omsCartItems) {
            //BigDecimal类型是专门处理钱运算的
            omsCartItem.setTotalPrice(omsCartItem.getPrice().multiply(BigDecimal.valueOf(omsCartItem.getQuantity())));
        }
        modelMap.put("cartList", omsCartItems);
        //被勾选的商品总额
        BigDecimal totalAmount = getTotalAmount(omsCartItems);
        modelMap.put("totalAmount", totalAmount);
        return "cartList";
    }

    private BigDecimal getTotalAmount(List<OmsCartItem> omsCartItems) {

        BigDecimal totalAmount = new BigDecimal("0");
        for (OmsCartItem omsCartItem : omsCartItems) {
            BigDecimal totalPrice = omsCartItem.getPrice().multiply(BigDecimal.valueOf(omsCartItem.getQuantity()));
            if (omsCartItem.getIsChecked().equals("1")) {
                totalAmount = totalAmount.add(totalPrice);
            }
        }
        return totalAmount;
    }

    @RequestMapping("checkCart")
    @LoginRequired(loginSuccess = false)
    public String checkCart(String isChecked, String skuId, HttpServletRequest request, HttpServletResponse response,
                            HttpSession session, ModelMap modelMap) {

        String memberId = "1";
        //调用服务  修改状态
        cartService.checkCart(skuId, memberId, isChecked);
        //将最新的数据从缓存中查出，渲染给内嵌页
        List<OmsCartItem> omsCartItems = cartService.cartList(memberId, skuId);
        modelMap.put("cartList", omsCartItems);
        //被勾选的商品总额
        BigDecimal totalAmount = getTotalAmount(omsCartItems);
        modelMap.put("totalAmount", totalAmount);
        return "cartListInner";
    }

    private Boolean if_cart_exist(List<OmsCartItem> omsCartItems, OmsCartItem omsCartItem) {

        Boolean b = false;
        for (OmsCartItem cartItem : omsCartItems) {
            String productSkuId = cartItem.getProductSkuId();
            if (productSkuId.equals(omsCartItem.getProductSkuId())) {
                b = true;
            }
        }
        return b;
    }

}
