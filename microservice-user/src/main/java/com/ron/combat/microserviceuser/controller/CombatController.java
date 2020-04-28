package com.ron.combat.microserviceuser.controller;

import com.ron.combat.microserviceuser.config.IdGenerator;
import com.ron.combat.microserviceuser.entity.User;
import com.ron.combat.microserviceuser.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequestMapping("combat")
@Api(description = "combat demo类")
public class CombatController {

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private UserMapper userMapper;

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

    @ApiOperation(value = "Sharding-JDBC demo 插入客户", httpMethod = "POST",notes ="")
    @PostMapping("user/save")
    public String save() {
        for (int i = 0; i <20 ; i++) {
            User user=new User();
            user.setName("test"+i);
            user.setCityId(1%2==0?1:2);
            user.setCreateTime(new Date());
            user.setSex(i%2==0?1:2);
            user.setPhone("11111111"+i);
            user.setEmail("xxxxx");
            user.setCreateTime(new Date());
            user.setPassword("eeeeeeeeeeee");
            userMapper.save(user);
        }

        return "success";
    }

    @ApiOperation(value = "Sharding-JDBC demo 查询客户", httpMethod = "POST",notes ="")
    @PostMapping("user/get/{id}")
    public User get(@PathVariable Long id) {
        User user =  userMapper.get(id);
        System.out.println(user.getId());
        return user;
    }

}
