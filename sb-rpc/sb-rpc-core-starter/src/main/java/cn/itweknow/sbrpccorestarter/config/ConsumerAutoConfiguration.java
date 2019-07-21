package cn.itweknow.sbrpccorestarter.config;

import cn.itweknow.sbrpccorestarter.anno.RpcConsumer;
import cn.itweknow.sbrpccorestarter.consumer.RpcProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.lang.reflect.Field;

/**
 * @author ganchaoyang
 * @date 2018/10/29 19:53
 * @description
 */
@Configuration
@ConditionalOnClass(RpcConsumer.class)
@EnableConfigurationProperties(RpcProperties.class)
public class ConsumerAutoConfiguration {

    @Autowired
    private RpcProxy rpcProxy;


    /**
     * 设置动态代理
     * @return
     */
    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName)
                    throws BeansException {
                Class<?> objClz = bean.getClass();
                for (Field field : objClz.getDeclaredFields()) {
                    RpcConsumer rpcConsumer = field.getAnnotation(RpcConsumer.class);
                    if (null != rpcConsumer) {
                        Class<?> type = field.getType();
                        field.setAccessible(true);
                        try {
                            field.set(bean, rpcProxy.create(type, rpcConsumer.providerName()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } finally {
                            field.setAccessible(false);
                        }
                    }
                }
                return bean;
            }
        };
    }

}
