package com.ron.combat.microserviceuser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("microservice-movie")
public interface RemoteClient {

    @GetMapping("condition/self")
    public String self();
}
