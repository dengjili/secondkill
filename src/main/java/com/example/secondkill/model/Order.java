/**
 * <p>Title: Order.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年5月24日  
 */
package com.example.secondkill.model;

import java.io.Serializable;

/** 
 * <p>Title: Order.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年5月24日  
 */
public class Order implements Serializable {
	
	private static final long serialVersionUID = 4214412997166944379L;
	
	private int id;
	private int productId;
	private float amount;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}
	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}
}