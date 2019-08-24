package cn.itweknow.sbelkstart.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ganchaoyang
 * @date 2019/5/1314:09
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributeLock {

    /**
     * 锁名称
     */
    String key();

    /**
     * 超时时间
     */
    long timeout() default 5;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
