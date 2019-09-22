package cn.itweknow.sbredis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SbRedisApplication.class})
public class TestOpsForSet {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testAdd() {
        redisTemplate.opsForSet().add("TestSet", "e1", "e2", "e3");
        long size = redisTemplate.opsForSet().size("TestSet");
        Assert.assertEquals(3L, size);
    }

    @Test
    public void testGet() {
        Set<String> testSet = redisTemplate.opsForSet().members("TestSet");
        System.out.println(testSet);
    }

    @Test
    public void testRemove() {
        redisTemplate.opsForSet().remove("TestSet", "e1", "e2");
        Set testSet = redisTemplate.opsForSet().members("TestSet");
        Assert.assertEquals("e3", testSet.toArray()[0]);
    }

}
