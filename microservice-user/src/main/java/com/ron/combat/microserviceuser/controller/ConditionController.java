package com.ron.combat.microserviceuser.controller;

import com.ron.combat.microserviceuser.service.RemoteClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ConditionController {

    @Autowired
    private RemoteClient remoteClient;

    @Value("${server.port}")
    private String port;

    @GetMapping("status")
    public boolean status(){
        return true;
    }

    @GetMapping("self")
    public String self(){
        log.info("request coming: "+port);
        return "microService user "+port;
    }

    @RequestMapping("/query")
    public List<String> query(String ids){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        return list;
    }

    @GetMapping("feignTest")
    public String feignTest(){
        String test = remoteClient.self();
        return "This is feign "+test;
    }

    
}
