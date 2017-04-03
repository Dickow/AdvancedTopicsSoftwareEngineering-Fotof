package org.dtu.fotof.models;

import java.io.Serializable;

public class Request implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Request(String type, Double price){
		this.price = price;
		this.requestName = type;
	}
	
	private Double price;
	private String requestName;

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getRequestName() {
		return requestName;
	}
	public void setRequestName(String name) {
		this.requestName = name;
	}
}
