package com.ron.combat.microservicesecskill.config;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class ControllerAop {

    private Gson gson = new Gson();

    /*@Autowired
    private ApiLogTimeService apiLogTimeService;
*/

    /**
     * 切点
     */
    @Pointcut("execution(* com.ron.combat.microservicesecskill.controller..*.*(..))")
    private void capControllerAop() {
    }

    @Before(value = "capControllerAop()")
    public void methodBefore(JoinPoint joinPoint) {
        log.info("-----------请求内容-----------");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
        log.info("uri={}", uri);
        String method = request.getMethod();
        log.info("method={}", method);
        String ip = request.getRemoteAddr();
        log.info("ip={}", ip);
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String name = joinPoint.getSignature().getName();
        log.info("classMethod={}", typeName + "." + name);
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            log.info("args={}", Arrays.toString(args));
        }
        log.info("-----------请求内容-----------");
    }

    @AfterReturning(returning = "response", pointcut = "capControllerAop()")
    public void methodAfterReturning(Object response) {
        log.info("-----------返回内容-----------");
        if (response != null) {
            log.info("response={}", gson.toJson(response));
        }
        log.info("-----------返回内容-----------");
    }

    @Around(value = "capControllerAop()")
    public Object methodAround(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        long startTime = System.currentTimeMillis();
        Object o = null;
        try {
            o = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        String excutionTime = Long.toString(endTime - startTime);
//        apiLogTimeService.addRecord(joinPoint,request,excutionTime);
        return o;
    }



}
