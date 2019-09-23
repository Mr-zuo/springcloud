package com.ron.combat.microservicemovie.controller;

import com.google.gson.Gson;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.ron.combat.microservicemovie.config.ServiceCommand;
import com.ron.combat.microservicemovie.config.ServiceObserveCommand;
import com.ron.combat.microservicemovie.service.ConditionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by ron on 2018/11/26.
 */
@Slf4j
@RestController
@RequestMapping("condition")
public class ConditionController {

    @Autowired
    private ConditionService conditionService;

    @Autowired
    private RestTemplate restTemplate;

    private Gson gson=new Gson();

    @Value("${server.port}")
    private String port;

    @GetMapping("status")
    public boolean status(){
        return true;
    }

    @GetMapping("self")
    public String self(){
        return "microService movie "+port;
    }

    @HystrixCommand(fallbackMethod = "testFallBack")
    @GetMapping("test")
    public String test(){
        String test=restTemplate.getForEntity("http://microservice-user/condition/self",String.class).getBody();
        return "This is "+test;
    }

    public String testFallBack(){
        return "error,please wait!";
    }


    @GetMapping("/testCommand")
    public String testCommand() throws ExecutionException, InterruptedException {
        ServiceCommand command = new ServiceCommand("self",restTemplate);
        Future<String> queue = command.queue();
        return queue.get();
    }

    @GetMapping("/testObserveCommand")
    public String testObserveCommand() throws ExecutionException, InterruptedException {
        List<String> list = new ArrayList<>();
        ServiceObserveCommand command = new ServiceObserveCommand("self",restTemplate);
        //热执行
//        Observable<String> observable = command.observe();
        //冷执行
        Observable<String> observable =command.toObservable();
        Thread.sleep(10000);
        //订阅
        observable.subscribe(new Observer<String>() {
            //请求完成的方法
            @Override
            public void onCompleted() {
                log.info("会聚完了所有查询请求");
            }
            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
            //订阅调用事件，结果会聚的地方，用集合去装返回的结果会聚起来。
            @Override
            public void onNext(String s) {
                log.info("结果来了.....");
                list.add(s);
            }
        });

        return list.toString();
    }


    //注解层面实现多请求
    @GetMapping("/hello")
    public String hello() throws ExecutionException, InterruptedException {
        Observable<String> stringObservable = conditionService.helloService();
        return gson.toJson(stringObservable);
    }


}
