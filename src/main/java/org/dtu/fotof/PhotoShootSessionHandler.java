package org.dtu.fotof;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.camunda.bpm.engine.cdi.BusinessProcess;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@ConversationScoped
public class PhotoShootSessionHandler implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private BusinessProcess businessProcess;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private BookingBusinessLogic bookingLogic;
	
	private Booking booking;
	protected Boolean customerPresent = true;
	private String date;
	
	public Booking getBooking(){
		if(booking == null){
			booking = bookingLogic.getBooking((Long)businessProcess.getVariable("bookingId"));
		}
		
		return booking;
	}
	
	public void photoshootCompleted() throws IOException, CustomerNotPresentException
	{
		if(getCustomerPresent() == true){
			bookingLogic.photoShootPerformed(booking);
		}
		else{
			throw new CustomerNotPresentException("Customer was not present");
		}
	}
	
	public Boolean getCustomerPresent() {
		return customerPresent;
	}

	public void setCustomerPresent(Boolean customerPresent) {
		this.customerPresent = customerPresent;
	}

	public String getDate(){
		return new Date().toString();
	}
	
}
