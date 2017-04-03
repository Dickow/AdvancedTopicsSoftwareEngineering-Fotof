package org.dtu.fotof;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
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
	
	@Inject
	private TaskForm taskForm;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private List<Request> availableRequests = Arrays.asList( 
			new Request("Red Eye Filtering", 25.5),
			new Request("Color filtering", 50.4),
			new Request("Contrast Changes", 30.1),
			new Request("Lighting filter", 12.1),
			new Request("Magic colors", 30.2),
			new Request("Black and White", 9.2),
			new Request("Greyscale", 5.5),
			new Request("Negate colors", 18.4));
	private List<Request> selectedRequests; 
	
	public String sendOrder(){
		try
		{
			businessProcess.setVariable("requests", selectedRequests);
			taskForm.completeTask();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("Could not save the chosen requests in the process context");
		}
		
		return "Order Sent";
	}

	public List<Request> getAvailableRequests() {
		return availableRequests;
	}

	public void setAvailableRequests(List<Request> availableRequests) {
		this.availableRequests = availableRequests;
	}

	public List<Request> getSelectedRequests() {
		return selectedRequests;
	}

	public void setSelectedRequests(List<Request> selectedRequests) {
		this.selectedRequests = selectedRequests;
	}
	
	
}
