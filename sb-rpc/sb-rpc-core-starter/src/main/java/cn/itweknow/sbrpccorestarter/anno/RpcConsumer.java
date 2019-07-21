package cn.itweknow.sbrpccorestarter.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ganchaoyang
 * @date 2018/10/26 17:08
 * @description
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcConsumer {

    /**
     * 服务名称
     * @return
     */
    String providerName();

}
