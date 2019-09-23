package com.ron.combat.discoverycenter.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ron on 2018/11/26.
 */
@Slf4j
@RestController
@RequestMapping("condition")
public class ConditionController {

    private Gson gson=new Gson();

    @Value("${server.port}")
    private String port;

    @Value("${eureka.instance.hostname}")
    private String hostname;

    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String serviceUrl;

    @GetMapping("status")
    public boolean status(){
        return true;
    }

    @GetMapping("addressInfo")
    public String addressInfo(){
        return "discovery-center:"+hostname+" "+port+"; "+"serviceUrl:"+serviceUrl;
    }

    /*@Scheduled(cron = "0/5 * * * * ?")
    public void addressInfo1(){
        log.info("discovery-center:"+hostname+" "+port+"; "+"serviceUrl:"+serviceUrl);
    }*/



}
