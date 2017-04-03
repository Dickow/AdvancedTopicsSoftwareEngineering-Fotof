package org.dtu.fotof;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.RuntimeErrorException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.dtu.fotof.models.Request;

@Named
@ConversationScoped
public class InputOrderProcessHandler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private BusinessProcess businessProcess;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private OrderBusinessLogic orderBusinessLogic;
	
	private List<Request> availableRequests = Arrays.asList(
			new Request("Red Eye Filtering", 25.5),
			new Request("Color filtering", 50.4),
			new Request("Contrast Changes", 30.1),
			new Request("Lighting filter", 12.1),
			new Request("Magic colors", 30.2),
			new Request("Black and White", 9.2),
			new Request("Greyscale", 5.5),
			new Request("Negate colors", 18.4));
	
	private ArrayList<String> selectedRequests;
	
	public void sendOrder(){
		try
		{
			// Save the chosen filters
			ArrayList<String> filters = new ArrayList<String>();
			Double price = 0.0;
			for (String req : selectedRequests) {
				// get the actual request
				Request request = this.findRequest(req);
				price += request.getPrice();
				filters.add(request.getRequestName());
			}
			
			businessProcess.setVariable("filters", filters);
			businessProcess.setVariable("filterPrice", price);
		}
		catch(Exception e){
			throw new RuntimeException("Could not save the chosen requests in the process context");
		}
		
		orderBusinessLogic.orderInput();
	}

	public List<Request> getAvailableRequests() {
		return availableRequests;
	}

	public void setAvailableRequests(List<Request> availableRequests) {
		this.availableRequests = availableRequests;
	}

	public List<String> getSelectedRequests() {
		return selectedRequests;
	}

	public void setSelectedRequests(ArrayList<String> selectedRequests) {
		this.selectedRequests = selectedRequests;
	}
	
	private Request findRequest(String name) throws RuntimeException{
		for (Request request : availableRequests) {
			if(request.getRequestName().equalsIgnoreCase(name)){
				return request;
			}
		}
		
		throw new RuntimeException("Could not find the right reuqest");
	}
	
}
