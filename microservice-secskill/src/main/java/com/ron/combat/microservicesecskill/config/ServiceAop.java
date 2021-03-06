package com.ron.combat.microservicesecskill.config;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Component
@Aspect
@Slf4j
public class ServiceAop {

   /* @Autowired
    private ApiLogExceptionService apiLogExceptionService;
*/

    @Pointcut("execution(* com.ron.combat.microservicesecskill.service.impl..*.*(..))")
    public void capServiceAop(){}

    @Before(value = "capServiceAop()")
    public void methodBefore(JoinPoint joinPoint){
        Class declaringType = joinPoint.getSignature().getDeclaringType();
        String name = joinPoint.getSignature().getName();
        log.info("{} 开始执行",declaringType+"."+name);
    }


    @After(value = "capServiceAop()")
    public void methodAfter(JoinPoint joinPoint){
        Class declaringType = joinPoint.getSignature().getDeclaringType();
        String name = joinPoint.getSignature().getName();
        log.info("{} 结束执行",declaringType+"."+name);
    }

    @AfterThrowing(pointcut = "capServiceAop()",throwing = "e")
    public void methodAfterThrowing(JoinPoint joinPoint, Throwable e){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes!=null){
            HttpServletRequest request = attributes.getRequest();
//            apiLogExceptionService.addRecord(joinPoint,request,e);
        }
    }


}
