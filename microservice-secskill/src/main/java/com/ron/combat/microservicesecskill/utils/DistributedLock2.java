package com.ron.combat.microservicesecskill.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 分布式锁的实现工具类
 * @author Administrator
 *
 */
@Slf4j
@Component
public class DistributedLock2 {

	private CuratorFramework client;  //zk客户端

	//用于挂起当前请求，并且等待上一个分布式锁释放
	private static CountDownLatch zkLockLatch=new CountDownLatch(1);

	//分布式锁的总结点名
	private static final String ZK_LOCK_PROJECT="combat_locks";
	//分布式锁节点
	private static final String DISTRIBUTED_LOCK="distributed_lock_";

	private String lockPath;

	public DistributedLock2(CuratorFramework client) {
		this.client = client;
	}

	/**
	 * 初始化锁
	 */
	@PostConstruct
	public void init() {
		log.info("init zk lock start");
		//使用命名空间
		client = client.usingNamespace("ZKLocks-Namespace");
		
		/**
		 * 创建zk锁的总节点，相当于eclipse工作空间下的项目
		 * 		ZKLocks-Namespace
		 * 			|
		 * 			——combat-locks
		 * 				|
		 * 				__distributed_lock
		 */
		
		try {
			if (client.checkExists().forPath("/"+ZK_LOCK_PROJECT)==null) {
				client.create()
					.creatingParentsIfNeeded()
					.withMode(CreateMode.PERSISTENT)
					.withACL(Ids.OPEN_ACL_UNSAFE)
					.forPath("/"+ZK_LOCK_PROJECT);
			}
			log.info("init zk lock end");
		} catch (Exception e) {
			log.error("客户端连接zookeeper服务器错误。。。请重试。。。");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取分布式锁
	 */
	public void getLock() throws Exception {
		try {
			//创建锁节点
			createLock();
			//尝试获取锁
			attemptLock();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private void attemptLock() throws Exception {
		// 获取Lock所有子节点，按照节点序号排序
		while(true) {
			List<String> lockPaths = null;
			lockPaths = client.getChildren().forPath("/" + ZK_LOCK_PROJECT);

			Collections.sort(lockPaths);
			int index = lockPaths.indexOf(lockPath.substring(ZK_LOCK_PROJECT.length() + 1));

			if (index == 0) {
				log.info(Thread.currentThread().getName() + " 锁获得, lockPath: " + lockPath);
				return;
			} else {
				// lockPath不是序号最小的节点，监控前一个节点
				String preLockPath = lockPaths.get(index - 1);
				Stat stat = client.checkExists().forPath(ZK_LOCK_PROJECT + "/" + preLockPath);
				addWatcherToLock(ZK_LOCK_PROJECT + "/" + preLockPath);
				// 假如前一个节点不存在了，比如说执行完毕，或者执行节点掉线，重新获取锁
				if (stat == null) {
					attemptLock();
				} else { // 阻塞当前进程，直到preLockPath释放锁，被watcher观察到，notifyAll后，重新acquireLock
					log.info(" 等待前锁释放，prelocakPath：" + preLockPath);
					try {
						//如果没有获取到锁，需要重新设置同步资源值
						if (zkLockLatch.getCount() <= 0) {
							zkLockLatch = new CountDownLatch(1);
						}
						//阻塞线程
						zkLockLatch.await();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

	}

	private void createLock() throws Exception {
		String lockPath = client.create()
				.creatingParentsIfNeeded()
				.withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
				.withACL(Ids.OPEN_ACL_UNSAFE) //设置 Znode 的访问权限
				.forPath("/" + ZK_LOCK_PROJECT + "/" + DISTRIBUTED_LOCK);
		log.info(Thread.currentThread().getName() + " 锁创建: "+lockPath);
		this.lockPath=lockPath;
	}


	/**
	 * 释放分布式锁
	 */
	public boolean releaseLock() {
		try {
			if (client.checkExists().forPath("/"+ZK_LOCK_PROJECT+"/"+DISTRIBUTED_LOCK)!=null) {
				client.delete().forPath("/"+ZK_LOCK_PROJECT+"/"+DISTRIBUTED_LOCK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		log.info("分布式锁释放完毕");
		return true;
	}

	/**
	 * 创建watcher监听
	 * @throws Exception
	 */
	public void addWatcherToLock(String path) throws Exception {
		final NodeCache nodeCache=new NodeCache(client,path,false);
		nodeCache.getListenable().addListener(new NodeCacheListener() {
			@Override
			public void nodeChanged() throws Exception {
				log.info("前锁释放："+nodeCache.getPath());
				zkLockLatch.countDown();
			}
		});
		nodeCache.start(true);

	}

}
