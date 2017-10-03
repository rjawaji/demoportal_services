package com.pgba.exception;


public class RestServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;

	/**
	 * @param message
	 */
	public RestServiceException(String message) {
		this.message = message;
	}

	/**
	 * @return the messageg
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}