/**
 * <p>Title: ProductService.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年5月24日  
 */
package com.example.secondkill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.secondkill.dao.ProductDao;
import com.example.secondkill.model.Product;

/** 
 * <p>Title: ProductService.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年5月24日  
 */
@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	@Transactional
	public Product getProductById(int productId) {
		return productDao.getProductById(productId);
	}

	@Transactional
	public int deductProductStock(int productId) {
		return productDao.deductProductStock(productId);
	}

	/**  
	 * <p>Title: listProduct</p>  
	 * <p>Description: </p>  
	 */
	public List<Product> listProduct() {
		return productDao.listProduct();
	}

}
