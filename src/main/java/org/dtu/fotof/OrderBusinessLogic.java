package org.dtu.fotof;

import java.io.IOException;
import java.util.List;
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
import org.dtu.fotof.models.PhotoOrder;
import org.dtu.fotof.models.Request;

@Stateless
@Named
public class OrderBusinessLogic {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static Logger LOGGER = Logger.getLogger(OrderBusinessLogic.class.getName());
	
	@Inject
	private TaskForm taskForm;
	
	public void registerOrder(DelegateExecution delegateExecution){
		PhotoOrder order = new PhotoOrder();
		Booking booking = entityManager.find(Booking.class, (Long)delegateExecution.getVariable("bookingId"));
		order.setBooking(booking.getId());
		// Save the requests chosen in a map
		try
		{
			Double price = (Double)delegateExecution.getVariable("filterPrice");
			order.setPrice(price + booking.getPrice());
			
			entityManager.persist(order);
			entityManager.flush();
			
			delegateExecution.removeVariables();
			delegateExecution.setVariable("bookingId", booking.getId());
			delegateExecution.setVariable("orderId", order.getId());
		}
		catch(Exception e){
			throw new RuntimeException("Failed to persist the order entity");
		}
	}
	
	public void orderInput(){
		try{
			LOGGER.log(Level.INFO, "Finishing the input order form");
			taskForm.completeTask();
		}
		catch(IOException e){
			throw new RuntimeException("Failed to complete the task form");
		}
	}
	
	public void chargeAmount(DelegateExecution delegateExecution){
		LOGGER.log(Level.INFO, "Order Id = " + delegateExecution.getVariable("orderId").toString());
		PhotoOrder order = entityManager.find(PhotoOrder.class, (Long)delegateExecution.getVariable("orderId"));
		Booking booking = entityManager.find(Booking.class, order.getBooking());
		String message = String.format(
				"Charging the amount: %.2f \n Customer: %s %s \n Mail: %s", 
				order.getPrice(), 
				booking.getFirstname(), 
				booking.getLastname(), 
				booking.getMail());
		LOGGER.log(Level.INFO, message);
	}
	
	public void performEditingCompleted(){
		try {
			taskForm.completeTask();
		} catch (IOException e) {
			throw new RuntimeException("Could not complete the perform editing task form");
		}
	}
	
	public void photoHandoffCompleted(){
		try {
			taskForm.completeTask();
		} catch (IOException e) {
			throw new RuntimeException("Could not complete the photo handoff task form");
		}
	}
	
	public void sendInvoice(DelegateExecution delegateExecution){
		PhotoOrder order = entityManager.find(PhotoOrder.class, (Long)delegateExecution.getVariable("orderId"));
		Booking booking = entityManager.find(Booking.class, order.getBooking());
		String message = String.format(
				"Sending the invoice for the amount: %.2f \n Customer: %s %s \n Mail: %s", 
				order.getPrice(), 
				booking.getFirstname(), 
				booking.getLastname(), 
				booking.getMail());
		LOGGER.log(Level.INFO, message);
		LOGGER.log(Level.INFO, "Invoice accepted");
	}
}
