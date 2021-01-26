package com.ygl.gmall.service;


import com.ygl.gmall.util.RedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
public class GmallManageServiceApplicationTests {
    @Autowired
    RedisUtil redisUtil;

    @Test
    public void contextLoads() {
        Jedis jedis = redisUtil.getJedis();
        System.out.println("查看结果："+jedis);
    }
}
