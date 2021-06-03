/**
 * <p>Title: Product.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年5月24日  
 */
package com.example.secondkill.model;

import java.io.Serializable;

/** 
 * <p>Title: Product.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年5月24日  
 */
public class Product  implements Serializable {
	
	private static final long serialVersionUID = 4214412947166944379L;
	
	private int id;
	
	private String name;
	
	private float price;
	
	private int stock;
	
	private String pic;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * @return the stock
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * @return the pic
	 */
	public String getPic() {
		return pic;
	}

	/**
	 * @param pic the pic to set
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}

}
