package cn.itweknow.sbaop.controller;

import cn.itweknow.sbaop.annotation.WebLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/hello")
    @WebLog(operation = "测试接口", request = "#{name}")
    public String hello(@RequestParam("name") String name){
        return name;
    }

}
