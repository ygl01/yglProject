package com.ygl.gmall.gmallredissontest.redissonTest;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.util.RedisUtil;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RedissonController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedissonClient redissonClient;

    @RequestMapping("/a")
    @ResponseBody
    public String a() {

        return "success";
    }

    //http://redistest.gmall.com
    @RequestMapping("testRedisson")
    @ResponseBody
    public String testRedisson(HttpServletRequest request) {

        Jedis jedis = redisUtil.getJedis();
        RLock lock = redissonClient.getLock("lock");//声明锁
        lock.lock();//加锁
        try {
            String v = jedis.get("k");
            if (StringUtils.isBlank(v)) {
                v = "1";
            }
            System.out.println("-》" + v);
            jedis.set("k", (Integer.parseInt(v) + 1) + "");

        } finally {
            jedis.close();
            lock.unlock();//解锁
        }

        return "success";
    }

}
