package cn.itweknow.sbelkstart.aop;


import cn.itweknow.sbelkstart.annotation.AnnotationResolver;
import cn.itweknow.sbelkstart.annotation.DistributeLock;
import cn.itweknow.sbelkstart.common.enums.ErrorCodeEnum;
import cn.itweknow.sbelkstart.common.model.BaseResponse;
import io.netty.util.internal.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ganchaoyang
 * @date 2019/5/1314:45
 */
@Aspect
@Component
@Order(99)
public class DistributeLockAspect {

    private Logger logger = LoggerFactory.getLogger(DistributeLockAspect.class);


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AnnotationResolver annotationResolver;


    @Pointcut("execution(* cn.itweknow.sbelkstart.controller..*.*(..))")
    public void distribute() {}


    @Around(value = "distribute()&& @annotation(distributeLock)")
    public Object doAround(ProceedingJoinPoint joinPoint, DistributeLock distributeLock) throws Exception {
        String key = annotationResolver.resolver(joinPoint, distributeLock.key());
        String keyValue = getLock(key, distributeLock.timeout(), distributeLock.timeUnit());
        if (StringUtil.isNullOrEmpty(keyValue)) {
            // 获取锁失败。
            return BaseResponse.addError(ErrorCodeEnum.OPERATE_FAILED, "请勿频繁操作");
        }
        // 获取锁成功
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            return BaseResponse.addError(ErrorCodeEnum.SYSTEM_ERROR, "系统异常");
        } finally {
            // 释放锁。
            unLock(key, keyValue);
        }
    }


    /**
     * 获取锁
     * @param key       锁的key
     * @param timeout   锁超时时间
     * @param timeUnit  时间单位
     *
     * @return 锁的值
     */
    private String getLock(String key, long timeout, TimeUnit timeUnit) {
        try {
            String value = UUID.randomUUID().toString();
            Boolean lockStat = stringRedisTemplate.execute((RedisCallback<Boolean>)connection ->
                    connection.set(key.getBytes(Charset.forName("UTF-8")), value.getBytes(Charset.forName("UTF-8")),
                            Expiration.from(timeout, timeUnit), RedisStringCommands.SetOption.SET_IF_ABSENT));
            if (!lockStat) {
                // 获取锁失败。
                return null;
            }
            return value;
        } catch (Exception e) {
            logger.error("获取分布式锁失败，key={}", key, e);
            return null;
        }
    }

    /**
     * 释放锁
     *
     * @param key    锁的key
     * @param value  获取锁的时候存入的值
     */
    private void unLock(String key, String value) {
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            boolean unLockStat = stringRedisTemplate.execute((RedisCallback<Boolean>)connection ->
                    connection.eval(script.getBytes(), ReturnType.BOOLEAN, 1,
                            key.getBytes(Charset.forName("UTF-8")), value.getBytes(Charset.forName("UTF-8"))));
            if (!unLockStat) {
                logger.error("释放分布式锁失败，key={}，已自动超时，其他线程可能已经重新获取锁", key);
            }
        } catch (Exception e) {
            logger.error("释放分布式锁失败，key={}", key, e);
        }
    }

}
