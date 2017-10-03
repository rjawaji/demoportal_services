/**
 * 
 */
package com.pgba.exception;

public class ConnectionFailureException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6566000401998933456L;
	
	private String message;

	/**
	 * @param message
	 */
	public ConnectionFailureException(String message) {
		this.message = message;
	}

	/**
	 * @return the message
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
