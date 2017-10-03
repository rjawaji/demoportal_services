package com.pgba.bean;

import java.io.Serializable;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;

@Configuration
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DemoPortalRequest  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4854105743172321744L;

	private String transactionId;
	private String type;
	
	private UserDetails user;

	
	/**
	 * @return the transactionid
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionid the transactionid to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the user
	 */
	public UserDetails getUser() {
		return user;
	}


}
