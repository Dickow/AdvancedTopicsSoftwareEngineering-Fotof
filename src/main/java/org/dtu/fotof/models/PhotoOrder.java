package org.dtu.fotof.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class PhotoOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PhotoOrder(){
		super();
	}
	
	@Id
	@GeneratedValue
	protected Long id;
	
	@Version
	protected Long version;
	
	protected Long booking;
	protected Double price;

	/**
	 * Calculates the total price, including the price of the booking
	 * @return
	 */
	public Double getPrice() {
		return price;
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

	public Long getBooking() {
		return booking;
	}

	public void setBooking(Long booking) {
		this.booking = booking;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
