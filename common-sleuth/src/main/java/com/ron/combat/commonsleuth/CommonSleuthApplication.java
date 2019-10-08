package com.ron.combat.commonsleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

@EnableZipkinServer
@SpringBootApplication
public class CommonSleuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonSleuthApplication.class, args);
    }

}
