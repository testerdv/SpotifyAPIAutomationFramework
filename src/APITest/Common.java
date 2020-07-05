package APITest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Common {
    private static Logger logger=LogManager.getLogger(Common.class);	
	static final String accessToken="YOUR_ACCESS_TOKEN_HERE";
	
	@BeforeSuite
	public void setBaseURI() {
		RestAssured.baseURI="https://api.spotify.com/v1/";
	}
	//check for requestspecs passing necessary or not...?	
	static public void
	check_error_code_error_message_with_invalid_id_in_path_param(String url, RequestSpecification requestSpecs) {
		logger.debug("common_check_error_code_error_message_with_invalid_id_in_path_param()");
		String invalidId="ghhggghg";
		
		Response response=requestSpecs.when().
		                			get(url,invalidId).
		                	  then().
		                	  		extract().response();
		
		Common.checkForErrorCodeAndErrorMessage(response,"invalid id");

	}


	static public void
	check_error_code_error_message_with_negative_id_in_path_param(String url,RequestSpecification requestSpecs) {
		logger.debug("In test check_error_code_error_message_with_negative_id_in_path_param()");
		Response response=requestSpecs.when().
				                     get(url,-1).
				              then().
				                     extract().response();
		
		Common.checkForErrorCodeAndErrorMessage(response,"invalid id");
		
	}
	
	static public void check_error_code_error_message_with_missing_id_in_path_param(String url,RequestSpecification requestSpecs) {
		logger.debug("In test check_error_code_error_message_with_missing_id_in_path_params():");
	String id="";
		
		Response response= requestSpecs.when().
									get(url,id).
							   then().
							         extract().response();
		Common.checkForErrorCodeAndErrorMessage(response,"invalid id");
		
	}
	
	public static  void checkPresenceOfKeys(Response response, List<String> responseKeys) {
		
		   
		   logger.debug("In method checkPresenceOfKeys:");
		   for(int i=0;i<responseKeys.size();i++) {
			   assertNotNull(response.jsonPath().get(responseKeys.get(i)));
			   logger.debug(responseKeys.get(i)+" asserted...");
		   }
    }	
 
		public static void	checkForErrorCodeAndErrorMessage(Response response,String message) {

			logger.debug("In method checkForErrorCodeAndErrorMessage():");
			
			assertNotNull(response.jsonPath().get("error"));
			logger.debug("error key Asserted...");
			
			assertNotNull(response.jsonPath().get("error.status"));
			logger.debug("error.status key Asserted...");
			
			assertNotNull(response.jsonPath().get("error.message"));
			logger.debug("error.message key Asserted...");
		
			assertEquals(response.statusCode(),400);
			logger.debug("error status code 400 Asserted...");
			
			
			assertEquals(response.jsonPath().get("error.message"),message);
			logger.debug("error message Asserted...");
			
		}

}
