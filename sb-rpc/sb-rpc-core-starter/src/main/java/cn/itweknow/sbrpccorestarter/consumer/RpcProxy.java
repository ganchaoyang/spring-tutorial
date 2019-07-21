package cn.itweknow.sbrpccorestarter.consumer;


import cn.itweknow.sbrpccorestarter.model.ProviderInfo;
import cn.itweknow.sbrpccorestarter.model.RpcRequest;
import cn.itweknow.sbrpccorestarter.model.RpcResponse;
import cn.itweknow.sbrpccorestarter.registory.ServiceDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author ganchaoyang
 * @date 2018/10/26 17:11
 * @description
 */
@Component
public class RpcProxy {

    @Autowired
    private ServiceDiscovery serviceDiscovery;

    @SuppressWarnings("unchecked")
    public <T> T create(Class<?> interfaceClass, String providerName) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                (proxy, method, args) -> {
            // 通过netty向Rpc服务发送请求。
            // 构建一个请求。
            RpcRequest request = new RpcRequest();
            request.setRequestId(UUID.randomUUID().toString())
                    .setClassName(method.getDeclaringClass().getName())
                    .setMethodName(method.getName())
                    .setParamTypes(method.getParameterTypes())
                    .setParams(args);
            // 获取一个服务提供者。
            ProviderInfo providerInfo = serviceDiscovery.discover(providerName);
            // 解析服务提供者的地址信息，数组第一个元素为ip地址，第二个元素为端口号。
           String[] addrInfo = providerInfo.getAddr().split(":");
            String host = addrInfo[0];
            int port = Integer.parseInt(addrInfo[1]);
            RpcClient rpcClient = new RpcClient(host, port);
            // 发送调用消息。
            RpcResponse response = rpcClient.send(request);
            if (response.isError()) {
                throw response.getError();
            } else {
                return response.getResult();
            }
        });
    }

    public void setServiceDiscovery(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }
}
