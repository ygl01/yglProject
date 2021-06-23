// Copyright (C) 2021 Focus Media Holding Ltd. All Rights Reserved.

package com.ygl.gmall.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author ygl
 * @version V1.0
 * @since 2021-06-22 17:15
 **/
public class TestJwt {

    public static void main(String[] args) {

        String key = "2021ygl0622";
        HashMap<String, Object> map = new HashMap<>();
        map.put("meberId", Md5.encodeByMD5("1"));
        map.put("nickName", Md5.encodeByMD5("zhangsan"));
        String ip = "127.0.0.1";
        SimpleDateFormat timeSim = new SimpleDateFormat("yyyyMMdd HHmmss");
        String timeFormat = timeSim.format(new Date());
        String encode = JwtUtil.encode(Md5.encodeByMD5(key), map, Md5.encodeByMD5(ip + timeFormat));
        System.out.println("加密token：" + encode);
        Map<String, Object> decode = JwtUtil.decode(encode, key, ip + timeFormat);
        System.out.println("解密token：" + decode);

    }

}
