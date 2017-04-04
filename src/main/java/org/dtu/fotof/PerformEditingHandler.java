package org.dtu.fotof;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.dtu.fotof.models.Request;

@Named
@ConversationScoped
public class PerformEditingHandler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private BusinessProcess businessProcess;
	
	@Inject
	private OrderBusinessLogic orderBusinessLogic;
	
	private List<String> requests;

	public void editingPerformed(){
		orderBusinessLogic.performEditingCompleted();
	}

	public List<String> getRequests() {
		if(requests == null){
			requests = (List<String>)businessProcess.getVariable("filters");
		}
		
		return requests;
	}

	public void setRequests(List<String> requests) {
		this.requests = requests;
	}
	
}
