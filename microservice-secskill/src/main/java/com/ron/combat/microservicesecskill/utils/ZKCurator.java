package com.ron.combat.microservicesecskill.utils;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ZKCurator {

	private CuratorFramework client; //zk客户端
	
	final static Logger log= LoggerFactory.getLogger(ZKCurator.class);

	public ZKCurator(CuratorFramework client) {
		this.client = client;
	}
	
	/**
	 * 初始化操作
	 */
	@PostConstruct
	public void init() {
		//使用命名空间
		client=client.usingNamespace("zk-curator-connector");
	}
	
	/**
	 * 判断zk是否连接
	 * @return
	 */
	public boolean isZKAlive() {
		return client.isStarted();
	}
	
}
