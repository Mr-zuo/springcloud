package com.ron.combat.microserviceuser.controller;

import com.ron.combat.microserviceuser.config.IdGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequestMapping("combat")
@Api(description = "combat demo类")
public class CombatController {

    @Autowired
    private IdGenerator idGenerator;

    @ApiOperation(value = "生成雪花id", httpMethod = "POST",notes ="")
    @PostMapping("snowflakeId")
    public void snowflakeId() {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 200; i++) {
            executorService.execute(() -> {
                log.info("分布式 ID: {}", idGenerator.snowflakeId());
            });
        }
        executorService.shutdown();
    }

}
