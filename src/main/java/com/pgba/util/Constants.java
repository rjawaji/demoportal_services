package com.pgba.util;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains many of the Status Codes and Descriptions
 * which will be used throughout the application and they are intended
 * to be used as Constants.
 * 
 * @author XDY9
 * 
 * @version 1.0 02-MAY-2017
 * 
 */

public class Constants {
	
	public static final int ERROR_STATUS = -100;
	public static final int NULL_REQUEST_ERROR = -200;
	public static final int EXCEPTION_ERROR = -300;
	public static final int MISSING_VALUES = 99;
	public static final int SUCCESS = 0;
		
	
	public static final String NULL_REQUEST_ERROR_DESCRIPTION = "Request is Null";
	public static final String EXCEPTION_ERROR_DESCRIPTION = "Exception occured in the try {} catch {} block";
	public static final String REQUEST_MISSING_VALUES = "Request is missing values";
	public static final String SUCCESSFUL_REQUEST_STRING = "SUCCESS";
	public static final String REQUEST_ERROR_STRING = "REQUEST_ERROR";
	
	public static final String TO_PROCESS = "TO_PROCESS";
	public static final String DAILY_E_FORM_UPLOAD_PROCESS = "DAILY_E_FORM_UPLOAD_PROCESS";
	
	public static Map<Integer, String> ERROR_CODES = new HashMap<Integer,String>();
	
	static{
		ERROR_CODES.put(0,"SUCCESS");
		ERROR_CODES.put(100,"ERROR");
		ERROR_CODES.put(101,"VALIDATION ERROR");
		ERROR_CODES.put(102,"NOT FOUND");
		ERROR_CODES.put(103,"USER ALREADY EXISTS");
		ERROR_CODES.put(104,"MFA CODE EXPIRED");
		ERROR_CODES.put(105,"NO MFA CODE TO VERIFY");
		ERROR_CODES.put(106,"INVALID PASSWORD");
		ERROR_CODES.put(107,"PASSWORD EXPIRED");
		ERROR_CODES.put(108,"PASSWORD NOT AVAILABLE");
		ERROR_CODES.put(109,"USER ACCOUNT LOCKED");
		ERROR_CODES.put(110,"RECERTIFICATION REQUIRED");
		ERROR_CODES.put(111,"DEFAULT PASSWORD");
		ERROR_CODES.put(112,"USER EXISTS IN PENDING STATUS");
	}
		
}
