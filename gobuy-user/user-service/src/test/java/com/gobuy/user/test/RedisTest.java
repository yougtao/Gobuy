package com.gobuy.user.test;


import com.gobuy.user.GobuyUserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GobuyUserApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testString() {
        redisTemplate.opsForValue().set("key1", "value");

        String val = (String) redisTemplate.opsForValue().get("key1");
        System.out.println("val1= " + val);
    }

}
