package org.dtu.fotof;

public class CustomerNotPresentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerNotPresentException(String msg){
		super(msg);
	}
}
