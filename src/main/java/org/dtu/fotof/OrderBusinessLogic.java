package org.dtu.fotof;

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
import org.dtu.fotof.models.Order;
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
		Order order = new Order();
		
		Booking booking = entityManager.find(Booking.class, (Long)delegateExecution.getVariable("bookingId"));
		order.setBooking(booking);
		
		// Save the requests chosen in a map
		try
		{
			List<Request> requests = (List<Request>)delegateExecution.getVariable("requests");
			
			for (Request req : requests) {
				order.setPrice(order.getPrice() + req.getPrice());
			}
			
			entityManager.persist(order);
			entityManager.flush();
			
			delegateExecution.removeVariables();
			delegateExecution.setVariable("bookingId", booking.getId());
			delegateExecution.setVariable("orderId", order.getId());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void chargeAmount(DelegateExecution delegateExecution){
		Order order = entityManager.find(Order.class, (Long)delegateExecution.getVariable("orderId"));
		String message = String.format(
				"Charging the amount: %d \n Customer: %s %s \n Mail: %s", 
				order.getPrice(), 
				order.getBooking().getFirstname(), 
				order.getBooking().getLastname(), 
				order.getBooking().getMail());
		LOGGER.log(Level.INFO, message);
	}
	
	public void performEditingCompleted(){
		
	}
	
	public void photoHandoffCompleted(){
		
	}
	
	public void sendInvoice(DelegateExecution delegateExecution){
		Order order = entityManager.find(Order.class, (Long)delegateExecution.getVariable("orderId"));
		String message = String.format(
				"Sending the invoice for the amount: %d \n Customer: %s %s \n Mail: %s", 
				order.getPrice(), 
				order.getBooking().getFirstname(), 
				order.getBooking().getLastname(), 
				order.getBooking().getMail());
		LOGGER.log(Level.INFO, message);
		
		LOGGER.log(Level.INFO, "Simulating wait time");
		try {
			Thread.sleep(1*1000*60*2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LOGGER.log(Level.INFO, "Invoice accepted");
	}
}
