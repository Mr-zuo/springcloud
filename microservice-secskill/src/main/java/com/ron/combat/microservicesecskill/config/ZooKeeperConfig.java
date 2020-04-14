package com.ron.combat.microservicesecskill.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ZooKeeperConfig {


    @Bean
    public CuratorFramework getCuratorFramework() {
        // 用于重连策略，1000毫秒是初始化的间隔时间，3代表尝试重连次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",5000,5000, retryPolicy);
        //必须调用start开始连接ZooKeeper
        client.start();

        return client;
    }

}
