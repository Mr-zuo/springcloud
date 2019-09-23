package com.ron.combat.microservicemovie.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.ron.combat.microservicemovie.service.HystrixService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Service("hystrixService")
public class HystrixServiceImpl implements HystrixService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCollapser(batchMethod = "query",collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds",value = "200")})
    public Future<String> combineAnnotation(long id){
        return null;
    }

    @HystrixCommand
    public List<String> query(List<Long> ids){
        System.out.println("request,param:"+ids.toString()+Thread.currentThread().getName());
        String[] result = restTemplate.getForEntity("http://microservice-user/condition/query?ids={1}",String[].class, StringUtils.join(ids,",")).getBody();
        return Arrays.asList(result);
    }
}
