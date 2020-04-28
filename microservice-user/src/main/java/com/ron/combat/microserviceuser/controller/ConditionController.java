package com.ron.combat.microserviceuser.controller;

import com.ron.combat.microserviceuser.service.RemoteClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ron on 2018/11/26.
 */
@Slf4j
@RestController
@RequestMapping("condition")
@Api(description = "当前项目状态类")
public class ConditionController {

    @Autowired
    private RemoteClient remoteClient;

    @Value("${server.port}")
    private String port;

    @ApiOperation(value = "运行状态" ,httpMethod = "POST")
    @PostMapping("status")
    public boolean status(){
        return true;
    }

    @ApiOperation(value = "端口号" ,httpMethod = "POST")
    @PostMapping("self")
    public String self(){
        log.info("request coming: "+port);
        return "microService user "+port;
    }

    @ApiOperation(value = "测试调用其他微服务" ,httpMethod = "POST")
    @PostMapping("feignTest")
    public String feignTest(){
        String test = remoteClient.self();
        return "This is feign "+test;
    }

    
}
