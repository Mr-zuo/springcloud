package com.ron.combat.microservicemovie.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class ServiceCommand extends HystrixCommand<String> {

    private RestTemplate restTemplate;

    public ServiceCommand(String commandGroupKey,RestTemplate restTemplate){
        super(HystrixCommandGroupKey.Factory.asKey(commandGroupKey));
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        log.info(Thread.currentThread().getName());
        String body = restTemplate.getForEntity("http://microservice-user/condition/self", String.class).getBody();
        return body;
    }

    @Override
    protected String getFallback() {
        return "please wait!";
    }

    //Hystrix的缓存
    @Override
    protected String getCacheKey() {
        return "self";
    }
}
