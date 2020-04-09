package com.ron.combat.microservicesecskill.service;

import org.apache.zookeeper.KeeperException;

public interface BuyService {

	/**
	 * @Description: 购买商品
	 */
	public void doBuyItem(String itemId);
	
	public boolean displayBuy(String itemId, Integer buyCounts);

    boolean displayBuy2(String itemId, Integer buyCounts);
}

