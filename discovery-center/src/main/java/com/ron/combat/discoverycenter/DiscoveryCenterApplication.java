package com.ron.combat.discoverycenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryCenterApplication.class, args);
    }

}
