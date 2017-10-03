/**
 * 
 */
package com.pgba.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2276131490387298380L;
	private List<String> missingFields = new ArrayList<String>();
	
	/**
	 * @param missingFields
	 */
	public InvalidRequestException(List<String> missingFields) {
		this.missingFields = missingFields;
	}
	
	/**
	 * @return the missingFields
	 */
	public List<String> getMissingFields() {
		return missingFields;
	}
	/**
	 * @param missingFields the missingFields to set
	 */
	public void setMissingFields(List<String> missingFields) {
		this.missingFields = missingFields;
	}
	
	

}
