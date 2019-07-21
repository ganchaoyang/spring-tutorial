package cn.itweknow.sbrpccorestarter.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ganchaoyang
 * @date 2018/10/30 14:39
 * @description
 */
public class BeanFactory {

    /**
     * 持有当前Rpc服务提供的所有的服务
     * 即有@RpcService注解的类
     */
    private static Map<Class<?>, Object> beans = new HashMap<>();

    private BeanFactory() {
    }

    public static void addBean(Class<?> interfaceClass, Object bean) {
        beans.put(interfaceClass, bean);
    }

    public static Object getBean(Class<?> interfaceClass) {
        return beans.getOrDefault(interfaceClass, null);
    }

}
