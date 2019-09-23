package com.ron.combat.microservicemovie.controller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.ron.combat.microservicemovie.config.BatchCommand;
import com.ron.combat.microservicemovie.config.ServiceCommand;
import com.ron.combat.microservicemovie.service.HystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@RestController
@RequestMapping("hystrix")
public class HystrixController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HystrixService hystrixService;

    //请求缓存
    //在同一用户请求的上下文中，相同依赖服务的返回数据始终保持一致。在当次请求内对同一个依赖进行重复调用，只会真实调用一次。在当次请求内数据可以保证一致性。
    @GetMapping("/cache")
    public String hystrixCache() {
        HystrixRequestContext.initializeContext();
        ServiceCommand command = new ServiceCommand("self", restTemplate);
        String execute = command.execute();
        ServiceCommand command1 = new ServiceCommand("self", restTemplate);
        String execute1 = command1.execute();
        return execute+","+execute1;
    }

    //请求合并---自定义实现
    @GetMapping("/combine")
    public String requestCombine() throws ExecutionException, InterruptedException {

        //请求合并
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        BatchCommand command = new BatchCommand(restTemplate,1L);
        BatchCommand command1 = new BatchCommand(restTemplate,2L);
        BatchCommand command2 = new BatchCommand(restTemplate,3L);

        //这里你必须要异步，因为同步是一个请求完成后，另外的请求才能继续执行，所以必须要异步才能请求合并
        Future<String> future = command.queue();
        Future<String> future1 = command1.queue();

        String r = future.get();
        String r1 = future1.get();

        Thread.sleep(1000);
        //可以看到前面两条命令会合并，最后一条会单独，因为睡了1000毫秒，而你请求设置要求在200毫秒内才合并的。
        Future<String> future2 = command2.queue();
        String r2 = future2.get();

        System.out.println(r);
        System.out.println(r1);
        System.out.println(r2);

        context.close();
        return null;
    }

    //请求合并----注解实现
    @GetMapping("/combineAnnotation")
    public String combineAnnotation() throws ExecutionException, InterruptedException {
        //请求合并
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Future<String> future1 = hystrixService.combineAnnotation(1L);
        Future<String> future2 = hystrixService.combineAnnotation(2L);

        String s = future1.get();
        String s1 = future2.get();

        Thread.sleep(1000);

        Future<String> future3 = hystrixService.combineAnnotation(3L);
        String s2 = future3.get();

        System.out.println(s);
        System.out.println(s1);
        System.out.println(s2);

        context.close();
        return null;
    }

}
