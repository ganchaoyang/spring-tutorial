package cn.itweknow.sbrpcconsumer.controller;

import cn.itweknow.sbrpcapi.service.HelloRpcService;
import cn.itweknow.sbrpccorestarter.anno.RpcConsumer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello-rpc")
public class HelloRpcController {


    @RpcConsumer(providerName = "provider")
    private HelloRpcService helloRpcService;

    @GetMapping("/hello")
    public String hello() {
        return helloRpcService.sayHello();
    }
}
