package com.ron.combat.microserviceuser.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Date;

@Slf4j
public class TMProxyFactory implements MethodInterceptor {

    /*@Autowired
    private TmallApiHitMapper tmallApiHitMapper;

    private Gson gson=new Gson();
*/

    private Object target;

    public TMProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        TmallApiHitMapper tmallApiHitMapper = SpringUtils.getBean(TmallApiHitMapper.class);
//        String requestId = CommonUtils.getlocal();
//        log.info("TMProxyFactory start, requestId:"+requestId);
//        TmallApiHit tmallApiHit = new TmallApiHit(requestId, CommonConstants.NUM_0, new Date(), new Date());
//        tmallApiHitMapper.insert(tmallApiHit);
        Object invoke = method.invoke(target, objects);
//        tmallApiHit.setHitStatus(CommonConstants.NUM_1);
//        tmallApiHit.setUpdateTime(new Date());
//        tmallApiHitMapper.updateByPrimaryKeySelective(tmallApiHit);
//        log.info("TMProxyFactory end, requestId:"+requestId);
        return invoke;
    }
}
