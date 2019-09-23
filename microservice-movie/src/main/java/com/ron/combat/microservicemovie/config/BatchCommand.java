package com.ron.combat.microservicemovie.config;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class BatchCommand extends HystrixCollapser<List<String>,String,Long> {


    private Long id;

    private RestTemplate restTemplate;

    //在200毫秒内进行请求合并，不在的话，放到下一个200毫秒
    public BatchCommand(RestTemplate restTemplate,Long id) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("batch"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                        .withTimerDelayInMilliseconds(200)));
        this.id = id;
        this.restTemplate = restTemplate;
    }

    //获取每一个请求的请求参数
    @Override
    public Long getRequestArgument() {
        return id;
    }

    //创建命令请求合并
    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Long>> collection) {
        List<Long> ids = new ArrayList<>(collection.size());
        ids.addAll(collection.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        HjcCommand command = new HjcCommand("hjc",restTemplate,ids);
        return command;
    }

    //合并请求拿到了结果，将请求结果按请求顺序分发给各个请求
    @Override
    protected void mapResponseToRequests(List<String> results, Collection<CollapsedRequest<String, Long>> collection) {
        System.out.println("分配批量请求结果。。。。");

        int count = 0;
        for (CollapsedRequest<String,Long> collapsedRequest : collection){
            String result = results.get(count++);
            collapsedRequest.setResponse(result);
        }
    }
}
