package com.ron.combat.microserviceuser.config;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class ProxyFactory {

//    @Autowired
//    private IntouchReqMongo intouchReqMongo;
//
//    @Autowired
//    private IntouchResMongo intouchResMongo;

    private Gson gson=new Gson();

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                String requestId = CommonUtils.getlocal();
//                intouchReqMongo.save(new IntouchReq(requestId,args));
//                log.info("proxy requestId:"+requestId+"args:"+gson.toJson(args));
                Object invoke = method.invoke(target, args);
//                intouchResMongo.save(new IntouchRes(requestId,invoke));
//                log.info("proxy requestId:"+requestId+"args:"+gson.toJson(invoke));
                return invoke;
            }
        });
    }

}
