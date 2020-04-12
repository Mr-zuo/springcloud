package com.ron.combat.microservicesecskill.controller;

import com.ron.combat.microservicesecskill.comon.StatusRes;
import com.ron.combat.microservicesecskill.service.BuyService;
import com.ron.combat.microservicesecskill.utils.ZKCurator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 订购商品controller
 */
@Slf4j
@RestController
@RequestMapping("condition")
public class PayController {
	
	@Autowired
	private BuyService buyService;
	
	@Autowired
	private ZKCurator zkCurator;

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@PostMapping("/buy")
	public StatusRes doGetlogin(@RequestParam("itemId") String itemId,@RequestParam("buyCounts") Integer buyCounts) {
		StatusRes statusRes=new StatusRes();
		boolean result = buyService.displayBuy(itemId,buyCounts);
		statusRes.setBean(result?"订单创建成功。。":"订单创建失败");
		return statusRes;
	}

	
	/**
	 * 判断zk是否连接
	 * @param
	 * @return
	 */
	@PostMapping("/isZKAlive")
	public StatusRes isZKAlive() {
		StatusRes statusRes=new StatusRes();
		boolean isAlive = zkCurator.isZKAlive();
		statusRes.setBean(isAlive?"连接。。":"断开");
		return statusRes;
	}
	
	
}
