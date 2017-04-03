package org.dtu.fotof;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;

@Named
@ConversationScoped
public class PhotoHandoffHandler implements Serializable{

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

	public void photoHandoffCompleted(){
		orderBusinessLogic.photoHandoffCompleted();
	}
}
