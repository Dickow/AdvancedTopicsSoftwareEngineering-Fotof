package org.dtu.fotof;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.delegate.DelegateExecution;

@Stateless
@Named
public class BookingBusinessLogic {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void persistBooking(DelegateExecution delegateExecution){
		
		// Get variables and instantiate object
		Booking booking = new Booking();
		Map<String, Object> processVariables = delegateExecution.getVariables();
		
		// Assign values
		booking.setCardExpireMonth((String)processVariables.get("card_expire_month"));
		booking.setCardExpireYear((String)processVariables.get("card_expire_year"));
		booking.setCardNumber((String)processVariables.get("card_number"));
		booking.setFirstname((String)processVariables.get("firstname"));
		booking.setLastname((String)processVariables.get("lastname"));
		booking.setMail((String)processVariables.get("mail"));
		
		// Persist the booking
		entityManager.persist(booking);
		entityManager.flush();
		
		// Remove the old process variables and save the new booking Id
		delegateExecution.removeVariables(processVariables.keySet());
		delegateExecution.setVariable("bookingId", booking.getId());
	}
}
