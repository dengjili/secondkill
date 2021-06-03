/**
 * <p>Title: OrderDao.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年5月24日  
 */
package com.example.secondkill.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.example.secondkill.model.Order;

/** 
 * <p>Title: OrderDao.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年5月24日  
 */
@Mapper
@Repository
public interface OrderDao {
	@Select("select * from c_order")
	List<Order> listOrders();
	
	@Select("select * from c_order where id = #{id}")
	Order getOrderById(int id);
	
	@Insert("insert into c_order(productId, amount) values(#{productId}, #{amount})")
	void saveOrder(Order order);
}
