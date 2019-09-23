package com.ron.combat.microservicemovie.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class HjcCommand extends HystrixCommand<List<String>> {

    private RestTemplate restTemplate;
    private List<Long> ids;


    public HjcCommand(String commandGroupKey, RestTemplate restTemplate, List<Long> ids) {
        //根据commandGroupKey进行线程隔离
        super(HystrixCommandGroupKey.Factory.asKey(commandGroupKey));
        this.restTemplate = restTemplate;
        this.ids = ids;
    }

    @Override
    protected List<String> run() throws Exception {
        log.info("request,param:："+ids.toString()+Thread.currentThread().getName());
        String[] result = restTemplate.getForEntity("http://microservice-user/condition/query?ids={1}",String[].class, StringUtils.join(ids,",")).getBody();
        return Arrays.asList(result);
    }
}
