package cn.itweknow.sbredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SbRedisApplication.class})
public class TestOpsForList {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testLeftPush() {
        redisTemplate.opsForList().leftPush("TestList", "TestLeftPush");
    }

    @Test
    public void testRightPush() {
        redisTemplate.opsForList().rightPush("TestList", "TestRightPush");
    }


    @Test
    public void testLeftPop() {
        Object leftFirstElement = redisTemplate.opsForList().leftPop("TestList");
        System.out.println(leftFirstElement);
    }

    @Test
    public void testRightPop() {
        Object rightFirstElement = redisTemplate.opsForList().rightPop("TestList");
        System.out.println(rightFirstElement);
    }

}
