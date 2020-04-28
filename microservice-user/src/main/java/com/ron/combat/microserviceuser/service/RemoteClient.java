package com.ron.combat.microserviceuser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 绑定已注册微服务应用name
 */
@FeignClient("microservice-movie")
public interface RemoteClient {

    /**
     * 调用对应微服务的api
     * @return
     */
    @GetMapping("condition/self")
    public String self();
}
