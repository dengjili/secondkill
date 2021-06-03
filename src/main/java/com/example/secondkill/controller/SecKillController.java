/**
 * <p>Title: SecKillController.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年5月24日  
 */
package com.example.secondkill.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.secondkill.constant.Constant;
import com.example.secondkill.model.Product;
import com.example.secondkill.service.ProductService;

/** 
 * <p>Title: SecKillController.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年5月24日  
 */
@RestController
@RequestMapping("/seckill")
public class SecKillController {
	
	private final Logger logger = LoggerFactory.getLogger(SecKillController.class);
	
	@Autowired
	private ProductService productService;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private ZooKeeper zooKeeper;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	private static ConcurrentMap<Integer, Boolean> prodocutSoldOutMap = new ConcurrentHashMap<>();
	
	public static ConcurrentMap<Integer, Boolean> getProdocutSoldOutMap() {
		return prodocutSoldOutMap;
	}
	
	@PostConstruct
	public void init() throws KeeperException, InterruptedException {
		List<Product> products = productService.listProduct();
		for (Product product : products) {
			int id = product.getId();
			String stock = product.getStock()+"";
			redisTemplate.opsForValue().set(Constant.REDIS_PRODUCT_STOCK_PREFIX + id, stock);
			// 监听 zookeeper 售完节点
			String productPath = "/" + Constant.PRODUCT_STOCK_PREFIX + "/" + id;
			if (zooKeeper.exists(productPath, true) == null) {
				zooKeeper.create(productPath, "false".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			zooKeeper.exists(productPath, true);
		}
		
	}
	
	@GetMapping("/{productId}")
	public Map<String, Object> secKill(@PathVariable("productId") int productId) throws KeeperException, InterruptedException {
		Map<String, Object> message = new HashMap<String, Object>();
		if (prodocutSoldOutMap.get(productId) != null && prodocutSoldOutMap.get(productId) == true) {
			message.put("code", "400");
			message.put("message", "商品库存已售罄");
			return message;
		}
		
		Long stock = redisTemplate.opsForValue().decrement(Constant.REDIS_PRODUCT_STOCK_PREFIX + productId);
		if (stock < 0) {
			prodocutSoldOutMap.put(productId, true);

			message.put("code", "400");
			message.put("message", "商品库存已售罄");
			logger.info("reids stock is less than 0.");
			redisTemplate.opsForValue().increment(Constant.REDIS_PRODUCT_STOCK_PREFIX + productId);

			// zookeeper 中设置售完标记， zookeeper 节点数据格式 product/1 true
			String productPath = "/" + Constant.PRODUCT_STOCK_PREFIX + "/" + productId;
			if (zooKeeper.exists(productPath, true) != null) {
				zooKeeper.setData(productPath, "true".getBytes(), -1);
			}
		} else {
			try {
				ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("test", productId+"");
				future.addCallback(o -> System.out.println("send-消息发送成功：" + o),
						throwable -> System.out.println("消息发送失败：" + throwable));

				message.put("code", "200");
				message.put("message", "创建订单成功");
			} catch (Exception e) {
				e.printStackTrace();
				message.put("code", "400");
				message.put("message", "创建订单失败，" + e.getMessage());
				redisTemplate.opsForValue().increment(Constant.REDIS_PRODUCT_STOCK_PREFIX + productId);
				if (prodocutSoldOutMap.get(productId) != null) {
					prodocutSoldOutMap.remove(productId);
				}
				// 通过 zookeeper 回滚其他服务器的 JVM 缓存中的商品售完标记
				String path = "/" + Constant.PRODUCT_STOCK_PREFIX + "/" + productId;
				if (zooKeeper.exists(path, true) != null) {
					zooKeeper.setData(path, "false".getBytes(), -1);
				}
			}
		}
		return message;
	}
}
