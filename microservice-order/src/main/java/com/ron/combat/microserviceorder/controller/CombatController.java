package com.ron.combat.microserviceorder.controller;


import com.ron.combat.microserviceorder.demo.Sender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("combat")
@Api(description = "combat demo类")
public class CombatController {

    @Autowired
    private Sender sender;

    @ApiOperation(value = "rabbitmq的send1", httpMethod = "POST",notes ="")
    @PostMapping(value = "send1")
    public void send1() throws Exception {
        sender.send1();
    }

    @ApiOperation(value = "rabbitmq的send2", httpMethod = "POST",notes ="")
    @PostMapping(value = "send2")
    public void send2() throws Exception {
        sender.send2();
    }

}
