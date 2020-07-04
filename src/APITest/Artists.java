package APITest;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Artists {
	private static Logger logger=LogManager.getLogger(Artists.class);	
	String responseString=null;
	Response response;
	RequestSpecification requestSpecs;
	
	@BeforeMethod
	public void setAccessToken() {
		
	requestSpecs=given().auth().preemptive().oauth2(Common.accessToken);
	
	}
	
	@Parameters({"artistId"})
	@Test	public void 
	get_an_artist_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null(String artistId) {
		logger.debug("......................................................................................");		
		logger.debug("Ïn test:get_an_artist_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null():");
		
	    //String id="0OdUWJ0sBjDrqHygGUXeCF";
		response=requestSpecs.when().
		      get("artists/{id}",artistId);
		
		response.then().
		      statusCode(200).contentType(JSON);
		
		
		//TODO:album_type
		
		List<String> responseKeys= new ArrayList<String>();
		
		//order of keys does not matter
		responseKeys.add("external_urls");
		responseKeys.add("genres"); //empty list is not null so genres is added here
		responseKeys.add("href");
		responseKeys.add("followers");
		responseKeys.add("id");
		responseKeys.add("images");
		responseKeys.add("name");
		responseKeys.add("popularity");
		responseKeys.add("type");
		responseKeys.add("uri");
		
		//System.out.println(response.asString());
		Common.checkPresenceOfKeys(response,responseKeys);
		logger.debug("TEST PASSED...");		
	   }
	
	@Parameters({"artistId"})	
	@Test(dependsOnMethods={"get_an_artist_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null"})
	public void
	get_an_artist_check_returned_keys_with_correct_datatype(String artistId) {
		logger.debug("......................................................................................");	
		logger.debug("In test get_an_artist_check_returned_keys_with_correct_datatype():");
		
				
		//String id="0OdUWJ0sBjDrqHygGUXeCF";
		response=requestSpecs.when().
		      get("artists/{id}",artistId);
		
		assertTrue((response.jsonPath().get("external_urls")) instanceof LinkedHashMap);
		logger.debug("external_urls asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("followers")) instanceof LinkedHashMap);
		logger.debug("followers asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("href")) instanceof String);
		logger.debug("href asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("genres")) instanceof ArrayList);
		logger.debug("genres asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("id")) instanceof String);
		logger.debug("id asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("images")) instanceof ArrayList);
		logger.debug("images asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("name")) instanceof String);
		logger.debug("name asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("popularity")) instanceof Integer);
		logger.debug("popularity asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("type")) instanceof String);
		logger.debug("type asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("uri")) instanceof String);
		logger.debug("uri asserted for datatype check...");
		
		logger.debug("TEST PASSED...");
}

	@Test
	public void
	get_an_artist_check_error_code_error_message_with_invalid_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_artist_check_error_code_error_message_with_invalid_id():");
		
		Common.check_error_code_error_message_with_invalid_id_in_path_param("artists/{id}", requestSpecs);
		
		logger.debug("TEST PASSED...");
	}
	
	@Test 
	public void
	get_an_artist_check_error_code_error_message_with_negative_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_artist_check_error_code_error_message_with_negative_id()");
		
		Common.check_error_code_error_message_with_negative_id_in_path_param("artists/{id}", requestSpecs);
		
		logger.debug("TEST PASSED...");
	}
	
	//DEFECT:/artists only does not run
	@Test
	public void get_an_artist_check_error_code_error_message_with_missing_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_artist_check_error_code_error_message_with_missing_id():");
		
		response= requestSpecs.when().
									get("artists").
							   then().
							         extract().response();
		Common.checkForErrorCodeAndErrorMessage(response,"invalid id");
				
		logger.debug("TEST PASSED...");
	}
	
	@Parameters({"artistId"})
	@Test	public void 
	get_an_artist_albums_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null(String artistId) {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_artist_album_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null():");
		
	    //String id="1vCWHaC5f2uS3yhpwWbIA6";
		response=requestSpecs.when().
		      get("artists/{id}/albums",artistId);
		
		response.then().
		      statusCode(200).contentType(JSON);
		     // extract().response();
		
		//TODO:album_type
		
		List<String> responseKeys= new ArrayList<String>();
		
		responseKeys.add("href");
		responseKeys.add("items");
		responseKeys.add("limit");
		responseKeys.add("offset");
		responseKeys.add("total");
		//assertNotNull(response.jsonPath().get("next") instanceof); //acc to docs it can be null
		//assertNotNull(response.jsonPath().get("previous") instanceof); //acc to docs it can be null
		
		int sizeOfItemsList=0;
		if((response.jsonPath().get("items")) instanceof ArrayList)  //check before casting
			sizeOfItemsList=((ArrayList<String>)(response.jsonPath().get("items"))).size();
		
		for(int i=0;i<sizeOfItemsList;i++) {
			responseKeys.add("items["+i+"].album_group");
			responseKeys.add("items["+i+"].album_type");
			responseKeys.add("items["+i+"].artists");
			responseKeys.add("items["+i+"].available_markets");
			responseKeys.add("items["+i+"].external_urls");
			responseKeys.add("items["+i+"].href");
			responseKeys.add("items["+i+"].id");
			responseKeys.add("items["+i+"].name");
			responseKeys.add("items["+i+"].images");
			responseKeys.add("items["+i+"].type");
			responseKeys.add("items["+i+"].uri");
		}
		
		Common.checkPresenceOfKeys(response,responseKeys);

		
		
		logger.debug("TEST PASSED...");
    }

	@Parameters({"artistId"})
	@Test(dependsOnMethods={"get_an_artist_albums_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null"})
	public void
	get_an_artist_albums_check_returned_keys_with_correct_datatype(String artistId) {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_artist_albums_check_returned_keys_with_correct_datatype():");
		
		//String id="1vCWHaC5f2uS3yhpwWbIA6";
		response=requestSpecs.when().
		      get("artists/{id}/albums",artistId);
		
				
		assertTrue((response.jsonPath().get("href")) instanceof String);
		logger.debug("href asserted...");
		
		assertTrue(response.jsonPath().get("items") instanceof ArrayList);
		logger.debug("items asserted...");
		
		assertTrue(response.jsonPath().get("limit") instanceof Integer);
		logger.debug("limit asserted...");
		
		assertTrue(response.jsonPath().get("offset") instanceof Integer);
		logger.debug("offset asserted...");
		
		
		
		assertTrue(response.jsonPath().get("total") instanceof Integer);
		logger.debug("total asserted...");
		
		int sizeOfItemsList=0;
		if((response.jsonPath().get("items")) instanceof ArrayList)  //check before casting
			sizeOfItemsList=((ArrayList<String>)(response.jsonPath().get("items"))).size();
		
			
		for(int i=0;i<sizeOfItemsList;i++) {
			assertTrue(response.jsonPath().get("items["+i+"].album_group") instanceof String);
			logger.debug("items["+i+"].album_group asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].album_type") instanceof String);
			logger.debug("items["+i+"].album_type asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].artists") instanceof ArrayList);
			logger.debug("items["+i+"].artists asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].available_markets") instanceof ArrayList);
			logger.debug("items["+i+"].available_markets asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].external_urls") instanceof LinkedHashMap);
			logger.debug("items["+i+"].external_urls asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].href") instanceof String);
			logger.debug("items["+i+"].href asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].id") instanceof String);
			logger.debug("items["+i+"].id asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].name") instanceof String);
			logger.debug("items["+i+"].name asserted...");
			
		
			assertTrue(response.jsonPath().get("items["+i+"].images") instanceof ArrayList);
			logger.debug("items["+i+"].images asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].type") instanceof String);
			logger.debug("items["+i+"].type asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].uri") instanceof String);
			logger.debug("items["+i+"].uri asserted...");
		}
			
		logger.debug("TEST PASSED...");
	}
	
	@Test 
	public void
	get_an_artist_albums_check_error_code_error_message_with_invalid_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_artist_albums_check_error_code_error_message_with_invalid_id():");
		
		Common.check_error_code_error_message_with_invalid_id_in_path_param("artists/{id}/albums",requestSpecs);
				
		logger.debug("TEST PASSED...");
	}
	
	@Test 
	public void
	get_an_artist_albums_check_error_code_error_message_with_negative_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_artist_albums_check_error_code_error_message_with_negative_id():");
		
		Common.check_error_code_error_message_with_negative_id_in_path_param("artists/{id}/albums", requestSpecs);
		
		logger.debug("TEST PASSED...");
	}
	
	@Test
	public void get_an_artist_albums_check_error_code_error_message_with_missing_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_artist_albums_check_error_code_error_message_with_missing_id():");
		
		Common.check_error_code_error_message_with_missing_id_in_path_param("artists/{id}/albums",requestSpecs);
		
		
		logger.debug("TEST PASSED...");
	}

	@Parameters({"artistId"})
	@Test	public void 
	get_an_artist_toptracks_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null(String artistId) {
		logger.debug("......................................................................................");
		logger.debug("Ïn test:get_an_artist_toptracks_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null():");
		
	    //String id="43ZHCT0cAZBISjO8DG9PnE";
		response=requestSpecs.given().
								param("country", "from_token").
							when().
								get("artists/{id}/top-tracks",artistId);
		
		response.then().
		      statusCode(200).contentType(JSON);
		
		//TODO:album_type
		
		System.out.println(response.asString());
		
		List<String> responseKeys= new ArrayList<String>();
		
		responseKeys.add("tracks");
		
		
		int sizeOfTracksList=0;
		if(response.jsonPath().get("tracks") instanceof ArrayList)  //check before cast
		sizeOfTracksList=((ArrayList<String>)(response.jsonPath().get("tracks"))).size();
		
		assertFalse((sizeOfTracksList>10));
		logger.debug("track list size <10 asserted...");
		
		for(int i=0;i<sizeOfTracksList;i++) {
			responseKeys.add("tracks["+i+"].album");
			responseKeys.add("tracks["+i+"].artists");
		//	responseKeys.add("tracks["+i+"].available_markets"); //DEFECT:if you print response this key is missing
			responseKeys.add("tracks["+i+"].disc_number");
			responseKeys.add("tracks["+i+"].duration_ms");
			responseKeys.add("tracks["+i+"].explicit");
			responseKeys.add("tracks["+i+"].external_ids");
			responseKeys.add("tracks["+i+"].external_urls");
			responseKeys.add("tracks["+i+"].href");
			responseKeys.add("tracks["+i+"].id");
			responseKeys.add("tracks["+i+"].is_playable");
		//	responseKeys.add("tracks["+i+"].linked_from");  //DEFECT:missing key
		//	responseKeys.add("tracks["+i+"].restrictions"); //DEFECT:missing key
			responseKeys.add("tracks["+i+"].name");
			responseKeys.add("tracks["+i+"].popularity");
			//responseKeys.add("tracks["+i+"].preview_url"); //value of this key can be null in docs. WORKAROUND NEEDED???
			responseKeys.add("tracks["+i+"].track_number");
			responseKeys.add("tracks["+i+"].type");
			responseKeys.add("tracks["+i+"].uri");
			
		
		}
		
		Common.checkPresenceOfKeys(response,responseKeys);
		
		logger.debug("TEST PASSED...");
    }

	@Parameters({"artistId"})
	@Test(dependsOnMethods={"get_an_artist_toptracks_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null"})
	public void
	get_an_artist_toptracks_check_returned_keys_with_correct_datatype(String artistId) {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_artist_toptracks_check_returned_keys_with_correct_datatype():");
		
		//TODO: insert log statements
		
		//String id="43ZHCT0cAZBISjO8DG9PnE";
		response=requestSpecs.given().
				param("country", "from_token").
			when().
				get("artists/{id}/top-tracks",artistId);

		assertTrue((response.jsonPath().get("tracks")) instanceof ArrayList);
		logger.debug("tracks asserted for datatype check...");
		
		
		int sizeOfTracksList=0;
		if(response.jsonPath().get("tracks") instanceof ArrayList)  //check before casting
			sizeOfTracksList=((ArrayList<String>)(response.jsonPath().get("tracks"))).size();
		

		for(int i=0;i<sizeOfTracksList;i++) {
		 
		  assertTrue(response.jsonPath().get("tracks["+i+"].album") instanceof LinkedHashMap);
		  logger.debug("trcaks["+i+"].album asserted for datatype check...");
		
		  assertTrue(response.jsonPath().get("tracks["+i+"].artists") instanceof ArrayList);
		  logger.debug("trcaks["+i+"].artists asserted for datatype check...");
		  
		  //assertTrue(response.jsonPath().get("tracks["+i+"].available_markets") instanceof ArrayList); //key missing
		  //logger.debug("trcaks["+i+"].available_markets asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].disc_number") instanceof Integer);
		  logger.debug("trcaks["+i+"].disc_number asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].duration_ms") instanceof Integer);
		  logger.debug("trcaks["+i+"].duration_ms asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].explicit") instanceof Boolean);
		  logger.debug("trcaks["+i+"].explicit asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].external_ids") instanceof LinkedHashMap);
		  logger.debug("trcaks["+i+"].external_ids asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].external_urls") instanceof LinkedHashMap);
		  logger.debug("trcaks["+i+"].external_urls asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].href") instanceof String);
		  logger.debug("trcaks["+i+"].href asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].id") instanceof String);
		  logger.debug("trcaks["+i+"].id asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].is_playable") instanceof Boolean);
		  logger.debug("trcaks["+i+"].is_playable asserted for datatype check...");
		  
		  //assertTrue(response.jsonPath().get("tracks["+i+"].linked_from") instanceof LinkedHashMap); //key missing
		  //logger.debug("trcaks["+i+"].linked_from asserted for datatype check...");
		  
		  //assertTrue(response.jsonPath().get("tracks["+i+"].restrictions") instanceof LinkedHashMap);//key missing
		  //logger.debug("trcaks["+i+"].restrictions asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].name") instanceof String);
		  logger.debug("trcaks["+i+"].name asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].popularity") instanceof Integer);
		  logger.debug("trcaks["+i+"].popularity asserted for datatype check...");
		  
		  //assertTrue(response.jsonPath().get("tracks["+i+"].preview_url") instanceof String);//value of this key can be null in docs. WORKAROUND NEEDED???
		  //logger.debug("trcaks["+i+"].preview_url asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].track_number") instanceof Integer);
		  logger.debug("trcaks["+i+"].track_number asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].type") instanceof String);
		  logger.debug("trcaks["+i+"].type asserted for datatype check...");
		  
		  assertTrue(response.jsonPath().get("tracks["+i+"].uri") instanceof String);
		  logger.debug("trcaks["+i+"].uri asserted for datatype check...");
		  
		}
		logger.debug("TEST PASSED...");
	}
	
	@Test
	public void
	get_an_artist_toptracks_check_error_code_error_message_with_invalid_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_artist_toptracks_check_error_code_error_message_with_invalid_id():");
		
		String invalidId="ghhggghg";
		
				
		response=requestSpecs.given().
				param("country", "from_token").
			when().
				get("artists/{id}/top-tracks",invalidId).
			then().
			   extract().response();

		
		
		Common.checkForErrorCodeAndErrorMessage(response,"invalid id");
		
	
		logger.debug("TEST PASSED...");
	}
	

	@Test 
	public void
	get_an_artist_toptracks_check_error_code_error_message_with_negative_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_artist_toptracks_check_error_code_error_message_with_negative_id()");
		
	
		response=requestSpecs.given().
				param("country", "from_token").
			when().
				get("artists/{id}/top-tracks",-1).
			then().
			   extract().response();

		Common.checkForErrorCodeAndErrorMessage(response,"invalid id");
		
			
		logger.debug("TEST PASSED...");
	}

	
	@Test
	public void get_an_artist_toptracks_check_error_code_error_message_with_missing_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_artist_toptracks_check_error_code_error_message_with_missing_id():");
		
		String missingId="";
		
		response=requestSpecs.given().
				param("country", "from_token").
			when().
				get("artists/{id}/top-tracks",missingId).
			then().
			   extract().response();
		
		Common.checkForErrorCodeAndErrorMessage(response,"invalid id");
		
			
		logger.debug("TEST PASSED...");
	}

}
