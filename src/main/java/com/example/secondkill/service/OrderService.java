/**
 * <p>Title: OrderService.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年5月24日  
 */
package com.example.secondkill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.secondkill.dao.OrderDao;
import com.example.secondkill.model.Order;
import com.example.secondkill.model.Product;

/** 
 * <p>Title: OrderService.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年5月24日  
 */
@Service
public class OrderService {
	
	@Autowired
	private ProductService service;
	@Autowired
	private OrderDao orderDao;
	
	@Transactional
	public void saveOrder(Order order) {
		orderDao.saveOrder(order);
	}
	
	@Transactional
	public void secKill(int productId) {
		Product product = service.getProductById(productId);
		
		if (product.getStock() < 0) {
			throw new RuntimeException("商品库存已售罄");
		}
		
		Order order = new Order();
		order.setProductId(productId);
		order.setAmount(product.getPrice());
		saveOrder(order);
		
		int update = service.deductProductStock(productId);
		if (update <= 0) {
			throw new RuntimeException("商品库存已售罄");
		}
	}
	
}
