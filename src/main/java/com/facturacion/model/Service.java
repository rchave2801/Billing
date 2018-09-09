package com.facturacion.model;

/**
 * 
 * @author rchave
 * Fecha de creación: 2017-03-04 (YYYY-MM-DD)
 */
public class Service {
	
	private int code;
	private String description;
	private String name;
	private int price;
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
