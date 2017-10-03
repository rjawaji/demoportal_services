package com.pgba.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pgba.bean.DemoPortalRequest;
import com.pgba.bean.DemoPortalResponse;
import com.pgba.bean.UserDetails;
import com.pgba.db.model.UserInfo;

@Configuration
@ComponentScan
public class DemoPortalUtil {

	private static final Logger logger = LogManager.getLogger(DemoPortalUtil.class.getName());
	
	public ObjectWriter jsonWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
	
	 /* 
     * @param dmeumRequest
     */
    public void logRequest(DemoPortalRequest demoPortalRequest) throws Exception{
    	
    	if(logger.isDebugEnabled()){
    		
			try {
				
				if(null == demoPortalRequest ){
					logger.error("@@@@@@@@@ JSON REQUEST IS NULL @@@@@@@ ");
					throw new NullPointerException ("JSON REQUEST IS NULL ");
				}else{
					logger.debug("REQUEST " + jsonWriter.writeValueAsString(demoPortalRequest));
				}
			} catch (JsonProcessingException e) {
				
			}
		}
    }
    
    public void setLoginMessage(DemoPortalResponse response,int stausCode, String userId){
    	
    	String err = Constants.ERROR_CODES.get(stausCode);
    	response.setStatusCode(stausCode);
    	if(stausCode != 0){
    		response.setStatus("LOGIN FAILED");
    		response.setError(err);
    	}else{
    		response.setStatus("SUCCESS");
    	}
    }
    
    /**
	 * 
	 * @param user
	 * @param response
	 */
	public void setUserDetails(UserInfo user, DemoPortalResponse response ){
		
		UserDetails userReq = new UserDetails();
		
		BeanUtils.copyProperties(user,userReq);
		response.setUserDetails(userReq);
		
	}
	
	/**
     * 
     * @return
     */
    public void setNotFound(DemoPortalResponse response, String error){
    	
    	response.setStatusCode(102);
		response.setStatus("NOT FOUND");
		response.setError(error);						
    }
    
    /**
     * 
     * @param response
     * @param error
     * @return
     */
    public void setValidationError(DemoPortalResponse response, String error){

	    
	    response.setStatus("VALIDATION ERROR");
		response.setStatusCode(101);
		response.setError(error);
	
    }
    
    /**
     * 
     * @return
     */
    public void setError(DemoPortalResponse response,Exception ex,String error){
    	
    	response.setStatusCode(100);
		response.setStatus("ERROR");
		response.setError(error);    		
    	response.setException(ExceptionUtils.getStackTrace(ex));		
	
    }
    
    public void setUserInfoList(DemoPortalResponse response,List<UserInfo> list) {
    	List<UserDetails> udList = new ArrayList<>();
    	UserDetails userDetails;
    	for(UserInfo ui:list) {
    		userDetails = new UserDetails();
    		BeanUtils.copyProperties(ui,userDetails);
    		udList.add(userDetails);
    	}
    	response.setUserDetailsList(udList);
    }
    
   
}
