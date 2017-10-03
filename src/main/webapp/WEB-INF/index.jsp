<!DOCTYPE html>
<html>
<head>
<title>DEMOPORTAL SERVICES</title>
</head>
<body  bgcolor="#ffffff" text="#000000"  >

<script type="text/javascript" src="/demoportal_services/js/jquery-3.2.1.min.js" ></script>
<script type="text/javascript">

function dmeSubmit(){		
	var request = document.dme.jsonrequest.value;
	
	request.replace("  ")
	var url = document.dme.servicename.value;
	alert(url)
		$.ajax({
			  type: "POST",
			  url: url,
			  data: request,			  
			  contentType : "application/json",
			  dataType: 'json',
			  success: function(data) {
				  document.dme.jsonresponse.value = JSON.stringify(data,null, '\t');			  
			  },
			  error: function(xhr, resp, text) {
				  document.dme.jsonresponse.value = text;
			  }		  
			});
	
}
function clearvalues(){		
	 document.dme.jsonrequest.value="";	
	 document.dme.jsonresponse.value="";
}
</script>
<img  src="/demoportal_services/images/logo1.jpg" width="100%"    align="middle" > 
<br><br>
<form name="dme" method="post">
<div align="center" >
<table  style="font-family: sans-serif; size: 10 px ; border: 1; " >

	<tr ><td> <b>Service Name:</b> </td></tr>
	<tr><td> 
	<select name="servicename" style="width: 250px;" >
	  <option value="getUsers">getUsers</option>
	  <option value="userRegistration">userRegistration</option>
	  <option value="sendMfaCode">sendMfaCode</option>
	  <option value="verifyMfaCode">verifyMfaCode</option>
	  <option value="setPassword">setPassword</option>
	  <option value="setMfaPreference">setMfaPreference</option>
	  <option value="setQuestions">setQuestions</option>
	  <option value="setUserStatus">setUserStatus</option>
	  <option value="verifyQuestions">verifyQuestions</option>
	  <option value="sendeFormFile">sendeFormFile</option>
	  <option value="login">login</option>	  
	  <option value="getUsersForApproval">getUsersForApproval</option>	  
	  <option value="sendTempPassword">sendTempPassword</option>	  
	  <option value="searchByUserId">searchByUserId</option>	  
	  <option value="updateUser">updateUser</option>	  
	  <option value="forgetUserId">forgetUserId</option>	  
	  <option value="searchByEidmId">searchByEidmId</option>
	</select>		
	</td></tr>	
	<tr><td> <b> Json Request: </b> </td></tr>
	<tr><td><textarea rows="15" cols="10"   style="width:600px;font-family: sans-serif; size: 14 px; border: 1 " name="jsonrequest">
	{
		"transactionId" : "b1bf3174-ff29-4265-ba4c-270ce847f3d4"
	}
	</textarea></td></tr>
	<tr><td align="right" >
					 				
					<%String ip = request.getRemoteAddr();					   
					 //if ( ip.equals("10.11.64.75") || ip.equals("10.11.64.132") ){ %>
					    <img alt="" src="/demoportal_services/images/clearall.jpg" style="cursor: pointer;"  onclick="clearvalues()"> &nbsp;
						<img alt="" src="/demoportal_services/images/submit.jpg" style="cursor: pointer;"  onclick="dmeSubmit()">
					<%//}%>
		</td>
		</tr>
 	<tr><td >  <b>Json Response: </b></td></tr>
 	<tr><td><textarea rows="15" cols="10" style="width:600px;font-family: sans-serif; size: 14 px; border: 1" name="jsonresponse"></textarea></td></tr>
</table>
</div> 		
</form>
</body>
</html>