package org.dtu.fotof.models;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Order(){
		super();
	}
	
	public Order(Request[] requests){
		for (Request request : requests) {
			price += request.getPrice();
		}
	}
	
	@Id
	@GeneratedValue
	protected Long id;
	
	protected Booking booking;
	
	protected Double price;

	/**
	 * Calculates the total price, including the price of the booking
	 * @return
	 */
	public Double getPrice() {
		return price + booking.getPrice();
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	
	
}
