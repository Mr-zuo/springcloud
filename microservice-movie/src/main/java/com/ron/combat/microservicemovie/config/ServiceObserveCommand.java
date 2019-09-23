package com.ron.combat.microservicemovie.config;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

@Slf4j
public class ServiceObserveCommand extends HystrixObservableCommand<String> {

    private RestTemplate restTemplate;


    public ServiceObserveCommand(String commandGroupKey, RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey(commandGroupKey));
        this.restTemplate = restTemplate;
    }

    @Override
    protected Observable<String> construct() {
        //观察者订阅网络请求事件
        return Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try{
                    if (!subscriber.isUnsubscribed()){
                        log.info("method start-----");
                        String result=restTemplate.getForEntity("http://microservice-user/condition/self",String.class).getBody();
                        //这个方法监听方法，是传递结果的，请求多次的结果通过它返回去汇总起来
                        subscriber.onNext(result);
                        String result1=restTemplate.getForEntity("http://microservice-user/condition/self",String.class).getBody();
                        //这个方法是监听方法，传递结果的
                        subscriber.onNext(result1);
                        subscriber.onCompleted();
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    //服务降级Fallback
    @Override
    protected Observable<String> resumeWithFallback() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext("error");
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
