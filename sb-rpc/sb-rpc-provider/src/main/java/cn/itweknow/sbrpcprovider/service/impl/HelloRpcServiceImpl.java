package cn.itweknow.sbrpcprovider.service.impl;

import cn.itweknow.sbrpcapi.service.HelloRpcService;
import cn.itweknow.sbrpccorestarter.anno.RpcService;

@RpcService(HelloRpcService.class)
public class HelloRpcServiceImpl implements HelloRpcService {

    @Override
    public String sayHello() {
        return "Hello RPC!";
    }
}
