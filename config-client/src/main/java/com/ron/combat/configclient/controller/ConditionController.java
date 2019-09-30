package com.ron.combat.configclient.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ron on 2018/11/26.
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("condition")
public class ConditionController {

    private Gson gson=new Gson();

    @Value("${server.port}")
    private String port;

    @Value("${envir}")
    private String envir;
    @Value("${name}")
    private String name;

    @GetMapping("status")
    public boolean status(){
        return true;
    }

    @GetMapping("self")
    public String self(){
        return "microService movie "+port;
    }

    @GetMapping("/test")
    public String test(){
        return this.name+", "+this.envir;
    }



}
