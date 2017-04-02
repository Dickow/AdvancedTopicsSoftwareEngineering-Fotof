package org.dtu.fotof;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Booking
 *
 */
@Entity
public class Booking implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Booking() {
		super();
	}
	
	@Id
	@GeneratedValue
	protected Long id;
   
	@Version
	protected Long version;
	
	protected String cardNumber;
	protected String cardExpireMonth;
	protected String cardExpireYear;
	protected String firstname;
	protected String lastname;
	protected String mail;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardExpireMonth() {
		return cardExpireMonth;
	}
	public void setCardExpireMonth(String cardExpireMonth) {
		this.cardExpireMonth = cardExpireMonth;
	}
	public String getCardExpireYear() {
		return cardExpireYear;
	}
	public void setCardExpireYear(String cardExpireYear) {
		this.cardExpireYear = cardExpireYear;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
