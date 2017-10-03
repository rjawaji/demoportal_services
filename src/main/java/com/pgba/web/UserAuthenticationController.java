package com.pgba.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pgba.bean.DemoPortalRequest;
import com.pgba.bean.DemoPortalResponse;
import com.pgba.db.model.UserInfo;
import com.pgba.db.service.UserInfoService;
import com.pgba.util.DemoPortalUtil;

@RestController
public class UserAuthenticationController {

	private static final Logger logger = LogManager.getLogger(UserAuthenticationController.class.getName());

	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	DemoPortalUtil demoUtil;
	
	@RequestMapping(value="/login", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public DemoPortalResponse login(@RequestBody DemoPortalRequest demoPortalRequest) { 
		/**
    	 * PSEUDO CODE
    	 *  1. Validate User Id & Password
    	 *  2. Send the success response
     	 *  
     	 *  ERROR CODES 
    	 *  106	INVALID PASSWORD
		 */
    	if(logger.isDebugEnabled()){
    		logger.debug("Start Method login");
    	}
    	
      	DemoPortalResponse response = new DemoPortalResponse();
	    
      	try{
      		demoUtil.logRequest(demoPortalRequest);
    		// Check for the Request / User object 
    		if(null != demoPortalRequest && null != demoPortalRequest.getUser()){
    			if(!StringUtils.isBlank(demoPortalRequest.getUser().getUserName()) && !StringUtils.isBlank(demoPortalRequest.getUser().getPassword())){
    				
    				UserInfo user = userInfoService.findByUserName(demoPortalRequest.getUser().getUserName());
	        		String password  = demoPortalRequest.getUser().getPassword();
	        		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        		if(null != user){
	        		
	        			boolean match = encoder.matches(password, user.getPassword());
	        			
	        			if(!match) {
	        				demoUtil.setLoginMessage(response, 106,user.getUserName());
	        			}
	        			else {
	        				demoUtil.setLoginMessage(response, 0 ,user.getUserName());
		        			demoUtil.setUserDetails(user, response);
	        			}
	        			}else{
	        				demoUtil.setNotFound(response, "USER ID NOT FOUND");
	        			}				
	        		}else{
	        			demoUtil.setValidationError(response,"USER ID/PASSWORD CODE IS BLANK");
	        		}								
    		}else{
    			demoUtil.setValidationError(response,"REQUEST/USER DETAILS IS BLANK");	    			
    		}
    		
      	}catch(Exception ex){
      		logger.error("Error in login " + ExceptionUtils.getStackTrace(ex));
		    demoUtil.setError(response,ex,"ERROR IN LOGIN");	    		
    	}  	
    	if(logger.isDebugEnabled()){				
			logger.debug("End Method login");
		}
    	return response;	
	}
	
	@RequestMapping(value="/getUsers", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public DemoPortalResponse getUsers(@RequestBody DemoPortalRequest demoPortalRequest) {
		DemoPortalResponse response = new DemoPortalResponse();
		try {
			List<UserInfo> userInfoList = userInfoService.getUserInfoList();
			demoUtil.setUserInfoList(response, userInfoList);
		}
		catch(Exception ex){
      		logger.error("Error in getUsers " + ExceptionUtils.getStackTrace(ex));
		    	    		
    	} 
		return response;	
		
	}
	
	    
}
