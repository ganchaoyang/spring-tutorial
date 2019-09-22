package cn.itweknow.sbredis;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SbRedisApplication.class})
public class TestOpsForHash {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testPut() {
        redisTemplate.opsForHash().put("TestHash", "FirstElement", "Hello,Redis hash.");
        Assert.assertTrue(redisTemplate.opsForHash().hasKey("TestHash", "FirstElement"));
    }

    @Test
    public void testGet() {
        Object element = redisTemplate.opsForHash().get("TestHash", "FirstElement");
        Assert.assertEquals("Hello,Redis hash.", element);
    }

    @Test
    public void testDel() {
        redisTemplate.opsForHash().delete("TestHash", "FirstElement");
        Assert.assertFalse(redisTemplate.opsForHash().hasKey("TestHash", "FirstElement"));
    }



}
