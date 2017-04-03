package org.dtu.fotof;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.dtu.fotof.models.Booking;

@Stateless
@Named
public class BookingBusinessLogic {

	@PersistenceContext
	private EntityManager entityManager;
	
	private static Logger LOGGER = Logger.getLogger(BookingBusinessLogic.class.getName());
	
	@Inject
	private TaskForm taskForm;
	
	public void persistBooking(DelegateExecution delegateExecution){
		
		// Get variables and instantiate object
		Booking booking = new Booking();
		Map<String, Object> processVariables = delegateExecution.getVariables();
		
		// Assign values
		booking.setCardExpireMonth((String)processVariables.get("cardExpireMonth"));
		booking.setCardExpireYear((String)processVariables.get("cardExpireYear"));
		booking.setCardNumber((String)processVariables.get("cardNumber"));
		booking.setFirstname((String)processVariables.get("firstname"));
		booking.setLastname((String)processVariables.get("lastname"));
		booking.setMail((String)processVariables.get("mail"));
		booking.setPhotographer((String)processVariables.get("photographer"));
		
		// Persist the booking
		entityManager.persist(booking);
		entityManager.flush();
		
		// Remove the old process variables and save the new booking Id
		delegateExecution.removeVariables(processVariables.keySet());
		delegateExecution.setVariable("bookingId", booking.getId());
	}
	
	public Booking getBooking(Long bookingId){
		LOGGER.log(Level.INFO, "Getting the order");
		return entityManager.find(Booking.class, bookingId);
	}
	
	public void photoShootPerformed(Booking booking) throws RuntimeException{
		try{
			taskForm.completeTask();
		}catch(IOException e){
			throw new RuntimeException("Could not complete the task", e);
		}
	}
	
	public void photoProcessingPerformed(Booking booking) throws RuntimeException{
		try{
			taskForm.completeTask();
		}
		catch(IOException e){
			throw new RuntimeException("Could not complete the task", e);
		}
	}
	
	public void sendNotification(DelegateExecution delegateExecution){
		Booking booking = entityManager.find(Booking.class, (Long)delegateExecution.getVariable("bookingId"));
		LOGGER.log(Level.INFO, String.format("Sending notification to the customer: %s %s \n Email: %s", booking.getFirstname(), booking.getLastname(), booking.getMail()));
	}
	
	public void sendInvoice(Booking booking){
		LOGGER.log(Level.INFO, String.format("Sending invoice to: %s %s. \n Price: %d", booking.getFirstname(), booking.getLastname(), booking.getPrice()));
	}
}
