package com.ron.combat.microservicesecskill.service.impl;

import com.ron.combat.microservicesecskill.entity.Orders;
import com.ron.combat.microservicesecskill.mapper.OrdersMapper;
import com.ron.combat.microservicesecskill.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {
	
	final static Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);
	
	@Autowired
	private OrdersMapper ordersMapper;
	
	@Override
	public Orders getOrder(String orderId) {
		return ordersMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public boolean createOrder(String itemId) {
		
		// 创建订单
		String oid = UUID.randomUUID().toString().replaceAll("-", "");
		Orders o = new Orders();
		o.setId(oid);
		o.setOrderNum(oid);
		o.setItemId(itemId);
		ordersMapper.insert(o);
		
		log.info("订单创建成功");
		
		return true;
	}

}

