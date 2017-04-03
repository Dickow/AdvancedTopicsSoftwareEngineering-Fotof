package org.dtu.fotof;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.dtu.fotof.models.Booking;

@Named
@ConversationScoped
public class PhotoProcessHandler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private BookingBusinessLogic bookingLogic;
	
	@Inject
	private BusinessProcess businessProcess;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Booking booking;
	private Boolean editingCompleted = false;
	private Boolean photosOrganized = false;
	private Boolean photosUploaded = false;
	
	public void processingCompleted(){
		if(getEditingCompleted() && getPhotosOrganized() && getPhotosUploaded()){
			bookingLogic.photoProcessingPerformed(booking);
		}
	}
	
	public Booking getBooking(){
		if(booking == null){
			booking = bookingLogic.getBooking((Long)businessProcess.getVariable("bookingId"));
		}
		
		return booking;
	}

	public Boolean getEditingCompleted() {
		return editingCompleted;
	}

	public void setEditingCompleted(Boolean editingCompleted) {
		this.editingCompleted = editingCompleted;
	}

	public Boolean getPhotosOrganized() {
		return photosOrganized;
	}

	public void setPhotosOrganized(Boolean photosOrganized) {
		this.photosOrganized = photosOrganized;
	}

	public Boolean getPhotosUploaded() {
		return photosUploaded;
	}

	public void setPhotosUploaded(Boolean photosUploaded) {
		this.photosUploaded = photosUploaded;
	}

	
	
}
