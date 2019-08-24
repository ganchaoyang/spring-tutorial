package cn.itweknow.sbelkstart.controller;


import cn.itweknow.sbelkstart.annotation.ControllerWebLog;
import cn.itweknow.sbelkstart.annotation.DistributeLock;
import cn.itweknow.sbelkstart.common.model.BaseRequest;
import cn.itweknow.sbelkstart.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Web日志测试
 * @author ganchaoyang
 */
@RestController
@RequestMapping("/weblog")
@Api(tags = "Web日志测试相关接口")
public class WebLogTestController {

    @GetMapping("/get-test")
    @ApiOperation("接口日志GET请求测试")
    @ControllerWebLog(name = "GET请求测试接口", intoDb = true)
    public String hello(@RequestParam("name") String name){
        return name;
    }

    @PostMapping("/post-test")
    @ApiOperation("接口日志POST请求测试")
    @ControllerWebLog(name = "接口日志POST请求测试", intoDb = true)
    public BaseResponse postTest(@RequestBody @Valid BaseRequest baseRequest, BindingResult bindingResult) {
        return BaseResponse.addResult();
    }

}
