package com.ron.combat.microserviceorder.controller;


import com.ron.combat.microserviceorder.demo.entity.Employee;
import com.ron.combat.microserviceorder.demo.rabbitmq.ack.MessageProducer;
import com.ron.combat.microserviceorder.demo.rabbitmq.simpleTopic.Sender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("combat")
@Api(description = "combat demo类")
public class CombatController {

    @Autowired
    private Sender sender;

    @Autowired
    private MessageProducer producer;

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

    /**
     * 发送对象消息
     * @return
     */
    @ApiOperation(value = "rabbitmq的send3", httpMethod = "POST",notes ="")
    @PostMapping(value = "send3")
    public String send3(){
        Employee employee = new Employee();
        employee.setAge(23);
        employee.setEmpno("007");
        employee.setName("jike");
        producer.sendMessage(employee);
        return "success";
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试死信队列. http://localhost:8082/dead/deadLetter?p=11234
     */
    @ApiOperation(value = "rabbitmq的send4", httpMethod = "POST",notes ="")
    @PostMapping(value = "deadLetter")
    public ResponseEntity.BodyBuilder deadLetter(@RequestParam("p") String p) {

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        // 声明消息处理器 这个对消息进行处理 可以设置一些参数 对消息进行一些定制化处理 我们这里 来设置消息的编码 以及消息的过期时间
        // 因为在.net 以及其他版本过期时间不一致 这里的时间毫秒值 为字符串
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            // 设置编码
            messageProperties.setContentEncoding("utf-8");
            // 设置过期时间10*1000毫秒
            messageProperties.setExpiration("10000");
            return message;
        };
        // 向DL_QUEUE 发送消息 10*1000毫秒后过期 形成死信,具体的时间可以根据自己的业务指定
        rabbitTemplate.convertAndSend("DL_EXCHANGE", "DL_KEY", p, messagePostProcessor, correlationData);
        return ResponseEntity.ok();
    }



}
