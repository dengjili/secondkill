/**
 * <p>Title: ProductDao.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年5月24日  
 */
package com.example.secondkill.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.example.secondkill.model.Product;

/** 
 * <p>Title: ProductDao.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年5月24日  
 */
@Mapper
@Repository
public interface ProductDao {

	@Update("update product set stock = stock - 1 where id = #{productId} and stock > 0")
	int deductProductStock(int productId);
	
	@Select("select * from product where id = #{id}")
	Product getProductById(int productId);

	@Select("select * from product")
	List<Product> listProduct();

}
