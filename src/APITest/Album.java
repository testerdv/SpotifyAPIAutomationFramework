package APITest;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.*;

public class Album {
 
    private static Logger logger=LogManager.getLogger(Album.class);	
	String responseString=null;
		
	Response response;
	RequestSpecification requestSpecs;
	
	
	@BeforeMethod
	public void setAccessToken() {
		
	requestSpecs=given().auth().preemptive().oauth2(Common.accessToken);
	
	}
	
	@Parameters({"albumId"})
	@Test	public void 
	get_an_album_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null(String albumId) {
		logger.debug("......................................................................................");
		logger.debug("Ïn test:get_an_album_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null():");

		//String id="0sNOF9WDwhWunNAHPD3Baj";
		response=requestSpecs.when().
		      get("albums/{id}",albumId);
		
		response.then().
		      statusCode(200).contentType(JSON);
		
	
		//TODO:album_type
		
		List<String> responseKeys= new ArrayList<String>();
		
		responseKeys.add("album_type");
		responseKeys.add("artists");
		
		int sizeOfArtistsList=0;
		if(response.jsonPath().get("artists") instanceof ArrayList)  //check before cast
		sizeOfArtistsList=((ArrayList<String>)(response.jsonPath().get("artists"))).size();
		
	
		for(int i=0;i<sizeOfArtistsList;i++) {
			responseKeys.add("artists["+i+"].href");
	
		}
		responseKeys.add("available_markets");
		responseKeys.add("copyrights");
		responseKeys.add("external_ids");
		responseKeys.add("external_urls");
		responseKeys.add("genres");
		responseKeys.add("id");
		responseKeys.add("images");
		responseKeys.add("label");
		responseKeys.add("name");
		responseKeys.add("popularity");
		responseKeys.add("release_date");
		responseKeys.add("release_date_precision");
		responseKeys.add("tracks");
		responseKeys.add("type");
		responseKeys.add("uri");
		
		Common.checkPresenceOfKeys(response,responseKeys);
		
		
		logger.debug("TEST PASSED...");
    }
	
	@Parameters({"albumId"})
	@Test(dependsOnMethods={"get_an_album_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null"})
	public void
	get_an_album_check_returned_keys_with_correct_datatype(String albumId) {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_album_check_returned_keys_with_correct_datatype():");

		//String id="0sNOF9WDwhWunNAHPD3Baj";
		response=requestSpecs.when().
		      get("albums/{id}",albumId);
		
		assertTrue((response.jsonPath().get("album_type")) instanceof String);
		logger.debug("album_type asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("artists")) instanceof ArrayList);
		logger.debug("artists asserted for datatype check...");
		
		int sizeOfArtistsList=0;
		if(response.jsonPath().get("artists") instanceof ArrayList)  //check before casting
			sizeOfArtistsList=((ArrayList<String>)(response.jsonPath().get("artists"))).size();
		
	
		for(int i=0;i<sizeOfArtistsList;i++) {
		  assertTrue(response.jsonPath().get("artists["+i+"].href") instanceof String);
		  logger.debug("artists["+i+"].href asserted for datatype check...");
		}
		assertTrue((response.jsonPath().get("available_markets")) instanceof ArrayList);
		logger.debug("available_markets asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("copyrights")) instanceof ArrayList);
		logger.debug("copyrights asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("external_ids")) instanceof LinkedHashMap);
		logger.debug("external_ids asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("external_urls")) instanceof LinkedHashMap);
		logger.debug("external_urls asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("genres")) instanceof ArrayList);
		logger.debug("genres asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("id")) instanceof String);
		logger.debug("id asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("images")) instanceof ArrayList);
		logger.debug("images asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("label")) instanceof String);
		logger.debug("label asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("name")) instanceof String);
		logger.debug("name asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("popularity")) instanceof Integer);
		logger.debug("popularity asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("release_date")) instanceof String);
		logger.debug("release_date asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("release_date_precision")) instanceof String);
		logger.debug("release_date_precision asserted for datatype check...");
		//assertTrue((response.jsonPath().get("tracks")) instanceof ArrayList);  //DEFECT:will fail here doc error
		
		assertTrue((response.jsonPath().get("type")) instanceof String);
		logger.debug("type asserted for datatype check...");
		
		assertTrue((response.jsonPath().get("uri")) instanceof String);
		logger.debug("uri asserted for datatype check...");
		
		logger.debug("TEST PASSED...");
	}
	
	
	@Test
	public void
	get_an_album_check_error_code_error_message_with_invalid_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_album_check_error_code_error_message_with_invalid_id():");
		
		Common.check_error_code_error_message_with_invalid_id_in_path_param("albums/{id}",requestSpecs);
		logger.debug("TEST PASSED...");
	}
	
	@Test 
	public void
	get_an_album_check_error_code_error_message_with_negative_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_album_check_error_code_error_message_with_negative_id()");
		
	
		Common.check_error_code_error_message_with_negative_id_in_path_param("albums/{id}", requestSpecs);
		
		logger.debug("TEST PASSED...");
	}

	//DEFECT:/albums only does not run
	@Test
	public void get_an_album_check_error_code_error_message_with_missing_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_album_check_error_code_error_message_with_missing_id():");
		
		//Common.check_error_code_error_message_with_missing_id_in_path_param("albums",requestSpecs);
		
		response= requestSpecs.when().
									get("albums").
							   then().
							         extract().response();
		Common.checkForErrorCodeAndErrorMessage(response,"invalid id");
		
		logger.debug("TEST PASSED...");
	}
	
	@Parameters({"albumId_two"})
	@Test 
	public void
	get_multiple_albums_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null(String albumIdTwo) {
		logger.debug("......................................................................................");
		logger.debug("In test get_multiple_albums_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null():");
		
		//String ids="0sNOF9WDwhWunNAHPD3Baj,6UXCm6bOO4gFlDQZV5yL37";
		response=requestSpecs.given().
									param("ids",albumIdTwo).
				             when().
		                          get("albums").
		                     then().
		                           statusCode(200).and().
		                           contentType(JSON).
		                           extract().response();
		
		      
		
		
		//TODO:album_type
		
		List<String> responseKeys= new ArrayList<String>();
		
		assertNotNull(response.jsonPath().get("albums"));
		
		int sizeOfAlbumList=0;
		if(response.jsonPath().get("albums") instanceof ArrayList)  //check before casting
		   sizeOfAlbumList=((ArrayList<String>)(response.jsonPath().get("albums"))).size();
		
		
		int sizeOfArtistsList=0;
		for(int i=0;i<sizeOfAlbumList;i++) {

			responseKeys.add("albums["+i+"].album_type");
		
			
			assertNotNull(response.jsonPath().get("albums["+i+"].artists"));
			if(response.jsonPath().get("albums["+i+"].artists") instanceof ArrayList)  //check before casting
			sizeOfArtistsList=((ArrayList<String>)(response.jsonPath().get("albums["+i+"].artists"))).size();
			
			System.out.println("#####artistslist size:"+sizeOfArtistsList);
			for(int j=0;j<sizeOfArtistsList;j++) {
				responseKeys.add("albums["+i+"].artists["+j+"].href");
		
			}
			responseKeys.add("albums["+i+"].available_markets");
			responseKeys.add("albums["+i+"].copyrights");
			responseKeys.add("albums["+i+"].external_ids");
			responseKeys.add("albums["+i+"].external_urls");
			responseKeys.add("albums["+i+"].genres");
			responseKeys.add("albums["+i+"].id");
			responseKeys.add("albums["+i+"].images");
			responseKeys.add("albums["+i+"].label");
			responseKeys.add("albums["+i+"].name");
			responseKeys.add("albums["+i+"].popularity");
			responseKeys.add("albums["+i+"].release_date");
			responseKeys.add("albums["+i+"].release_date_precision");
			responseKeys.add("albums["+i+"].tracks");
			responseKeys.add("albums["+i+"].type");
			responseKeys.add("albums["+i+"].uri");
		}
		
		Common.checkPresenceOfKeys(response,responseKeys);
		
		
		
		logger.debug("TEST PASSED...");
	}
	
	@Parameters({"albumId_two"})
	@Test(dependsOnMethods={"get_multiple_albums_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null"}) 
	public void
	get_multiple_albums_check_returned_keys_with_correct_datatype(String albumIdTwo) {
     
		logger.debug("......................................................................................");
		logger.debug("In test get_multiple_albums_check_returned_keys_with_correct_datatype():");

		
		//String ids="0sNOF9WDwhWunNAHPD3Baj,6UXCm6bOO4gFlDQZV5yL37";
		response=requestSpecs.given().
									param("ids",albumIdTwo).
				             when().
		                          get("albums");
		
			int sizeOfAlbumList=0;
			 
		    boolean albumIsInstanceOfArrayList=false; 
			if(response.jsonPath().get("albums") instanceof ArrayList) {  //check before casting
				sizeOfAlbumList=((ArrayList<String>)(response.jsonPath().get("albums"))).size();
				albumIsInstanceOfArrayList=true;
			}
		
			assertTrue(albumIsInstanceOfArrayList);
				
		
			int sizeOfArtistsList=0;
			boolean artistIsInstanceOfArrayList=false;
			for(int i=0;i<sizeOfAlbumList;i++) {
				assertTrue((response.jsonPath().get("albums["+i+"].album_type")) instanceof String);
				logger.debug(" albums["+i+"].album_type asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].artists")) instanceof ArrayList);
				logger.debug("albums["+i+"].artists asserted...");
				
				if(response.jsonPath().get("albums["+i+"].artists") instanceof ArrayList) {  //check before casting
					sizeOfArtistsList=((ArrayList<String>)(response.jsonPath().get("albums["+i+"].artists"))).size();
					artistIsInstanceOfArrayList=true;
				}
				
				assertTrue(artistIsInstanceOfArrayList);
				for(int j=0;j<sizeOfArtistsList;j++) {
					assertTrue(response.jsonPath().get("albums["+i+"].artists["+j+"].href") instanceof String);
					logger.debug("albums["+i+"].artists["+j+"].href asserted...");
				}	
				
				assertTrue((response.jsonPath().get("albums["+i+"].available_markets")) instanceof ArrayList);
				logger.debug("albums["+i+"].available_markets asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].copyrights")) instanceof ArrayList);
				logger.debug("albums["+i+"].copyrights asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].external_ids")) instanceof LinkedHashMap);
				logger.debug("albums["+i+"].external_ids asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].external_urls")) instanceof LinkedHashMap);
				logger.debug("albums["+i+"].external_urls asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].genres")) instanceof ArrayList);
				logger.debug("albums["+i+"].genres asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].id")) instanceof String);
				logger.debug("albums["+i+"].id asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].images")) instanceof ArrayList);
				logger.debug("albums["+i+"].images asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].label")) instanceof String);
				logger.debug("albums["+i+"].label asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].name")) instanceof String);
				logger.debug("albums["+i+"].name asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].popularity")) instanceof Integer);
				logger.debug("albums["+i+"].popularity asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].release_date")) instanceof String);
				logger.debug("albums["+i+"].release_date asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].release_date_precision")) instanceof String);
				logger.debug("albums["+i+"].release_date_precision asserted...");
				
				//assertTrue((response.jsonPath().get("albums["+i+"].tracks")) instanceof ArrayList);  //DEFECT:will fail here doc error in doc it is mentioned as array but it is json objecti.e. linkedhashmap
				//logger.debug("albums["+i+"].tracks asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].type")) instanceof String);
				logger.debug("albums["+i+"].type asserted...");
				
				assertTrue((response.jsonPath().get("albums["+i+"].uri")) instanceof String);
				logger.debug("albums["+i+"].uri asserted...");
	
				
			}
		logger.debug("TEST PASSED...");
	}
	
	@Test 
	public void
	get_multiple_albums_check_null_if_invalid_id() {
		
		logger.debug("......................................................................................");
		logger.debug("In test get_multiple_albums_check_null_if_invalid_id():");
		
		String ids="0sNOF9WDwhWunNAHPD3Baj,ghghgh"; //second id is invalid so second part should be null
		response=requestSpecs.given().
									param("ids",ids).
				             when().
		                          get("albums").
		                     then().
		                           extract().response();
     	assertNull(response.jsonPath().get("albums[1]"));

		
		logger.debug("TEST PASSED...");
	}
	
	@Test 
	public void
	get_multiple_albums_check_error_code_error_message_if_no_ids() {
		logger.debug("......................................................................................");
		logger.debug("In test get_multiple_albums_check_error_code_error_message_if_no_ids():");
		
		String ids="";  //no ids
		response=requestSpecs.given().
									param("ids",ids).
				             when().
		                          get("albums").
		                     then().
		                     		extract().response();
		Common.checkForErrorCodeAndErrorMessage(response,"invalid id");
		logger.debug("TEST PASSED...");
	}
	
	@Test 
	public void
	get_multiple_albums_check_error_code_error_message_if_negative_ids() {
		logger.debug("......................................................................................");
		logger.debug("In test get_multiple_albums_check_error_code_error_message_if_negative_ids():");
		
		String ids="-1,-1";  //no ids
		response=requestSpecs.given().
									param("ids",ids).
				             when().
		                          get("albums").
		                     then().
		                     		extract().response();
		
		Common.checkForErrorCodeAndErrorMessage(response,"invalid id");
		logger.debug("TEST PASSED...");
	}
	
	@Parameters({"albumId_more_than_twenty"})
	@Test
	public void
	get_multiple_albums_check_error_code_error_message_if_more_than_20_ids(String albumIdMoreThanTwenty) {
		logger.debug("......................................................................................");
		logger.debug("In test get_multiple_albums_check_error_code_error_message_if_more_than_20_ids():");
		
		response=requestSpecs.given().
									param("ids",albumIdMoreThanTwenty).
				             when().
		                          get("albums").
		                     then().
		                     		extract().response();
		
		      
		
		
		Common.checkForErrorCodeAndErrorMessage(response,"too many ids requested");
		logger.debug("TEST PASSED...");
	}

	
	@Test
	public void
	get_multiple_albums_check_duplicate_items_if_duplicate_keys() {
		logger.debug("......................................................................................");
		logger.debug("In test get_multiple_albums_check_duplicate_items_if_duplicate_keys():");
		
		String ids="0sNOF9WDwhWunNAHPD3Baj,0sNOF9WDwhWunNAHPD3Baj";  //duplicate ids
		response=requestSpecs.given().
									param("ids",ids).
				             when().
		                          get("albums").
		                     then().
		                     		statusCode(200).
		                            extract().response();
		
		//album is arraylist. It will be equal when each and every element and its size will also be equal.
		assertTrue((response.jsonPath().get("albums[0]")).equals((response.jsonPath().get("albums[1]"))));
		
		logger.debug("TEST PASSED...");
	}
	
	@Parameters({"albumId"})
	@Test	public void 
	get_an_album_tracks_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null(String albumId) {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_album_tracks_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null():");
		
	    //String id="0sNOF9WDwhWunNAHPD3Baj";
		response=requestSpecs.when().
		      get("albums/{id}/tracks",albumId);
		
		response.then().
		      statusCode(200).contentType(JSON);
		     // extract().response();
		
		List<String> responseKeys= new ArrayList<String>();
		
		responseKeys.add("href");
		responseKeys.add("items");
		responseKeys.add("limit");
		responseKeys.add("offset");
		responseKeys.add("total");
		
		int sizeOfItemsList=0;
		if((response.jsonPath().get("items")) instanceof ArrayList)  //check before casting
			sizeOfItemsList=((ArrayList<String>)(response.jsonPath().get("items"))).size();
		
		for(int i=0;i<sizeOfItemsList;i++) {
			responseKeys.add("items["+i+"].artists");
			responseKeys.add("items["+i+"].available_markets");
			responseKeys.add("items["+i+"].disc_number");
			responseKeys.add("items["+i+"].duration_ms");
			responseKeys.add("items["+i+"].explicit");
			responseKeys.add("items["+i+"].external_urls");
			responseKeys.add("items["+i+"].href");
			responseKeys.add("items["+i+"].id");
	//		responseKeys.add("items["+i+"].is_playable"); //DEFECT:No such item present in response
	//		responseKeys.add("items["+i+"].linked_from"); //DEFECT:No such item present in response
			responseKeys.add("items["+i+"].name");
	//		responseKeys.add("items["+i+"].preview_url"); //DEFECT:preview_url comes as null in result response
			responseKeys.add("items["+i+"].track_number");
			responseKeys.add("items["+i+"].type");
			responseKeys.add("items["+i+"].uri");
		}
		Common.checkPresenceOfKeys(response,responseKeys);

	logger.debug("TEST PASSED...");
    }
	
	@Parameters({"albumId"})
	@Test(dependsOnMethods={"get_an_album_tracks_check_status_code_is_200_json_format_and_all_necessary_keys_present_and_not_null"})
	public void
	get_an_album_tracks_check_returned_keys_with_correct_datatype(String albumId) {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_album_tracks_check_returned_keys_with_correct_datatype():");
		
		//String id="0sNOF9WDwhWunNAHPD3Baj";
		response=requestSpecs.when().
		      get("albums/{id}/tracks",albumId);
		
				
		assertTrue((response.jsonPath().get("href")) instanceof String);
		logger.debug("href asserted...");
		
		assertTrue(response.jsonPath().get("items") instanceof ArrayList);
		logger.debug("items asserted...");
		
		assertTrue(response.jsonPath().get("limit") instanceof Integer);
		logger.debug("limit asserted...");
		
		//assertNotNull(response.jsonPath().get("next") instanceof); //acc to docs it can be null
		
		assertTrue(response.jsonPath().get("offset") instanceof Integer);
		logger.debug("offset asserted...");
		
		//assertNotNull(response.jsonPath().get("previous") instanceof); //acc to docs it can be null
		
		assertTrue(response.jsonPath().get("total") instanceof Integer);
		logger.debug("total asserted...");
		
		int sizeOfItemsList=0;
		if((response.jsonPath().get("items")) instanceof ArrayList)  //check before casting
			sizeOfItemsList=((ArrayList<String>)(response.jsonPath().get("items"))).size();
		
		for(int i=0;i<sizeOfItemsList;i++) {
			assertTrue(response.jsonPath().get("items["+i+"].artists") instanceof ArrayList);
			logger.debug("items["+i+"].artists asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].available_markets") instanceof ArrayList);
			logger.debug("items["+i+"].available_markets asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].disc_number") instanceof Integer);
			logger.debug("items["+i+"].disc_number asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].duration_ms") instanceof Integer);
			logger.debug("items["+i+"].duration_ms asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].explicit") instanceof Boolean);
			logger.debug("items["+i+"].explicit asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].external_urls") instanceof LinkedHashMap);
			logger.debug("items["+i+"].external_urls asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].href") instanceof String);
			logger.debug("items["+i+"].href asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].id") instanceof String);
			logger.debug("items["+i+"].id asserted...");
			
			//assertTrue(response.jsonPath().get("items["+i+"].is_playable") instanceof Boolean); //No such item in response
			//assertTrue(response.jsonPath().get("items["+i+"].linked_from") instanceof LinkedHashMap); //No such item in response
			assertTrue(response.jsonPath().get("items["+i+"].name") instanceof String);
			logger.debug("items["+i+"].name asserted...");
			
		//	assertTrue(response.jsonPath().get("items["+i+"].preview_url") instanceof String); // comes out as null in response
			assertTrue(response.jsonPath().get("items["+i+"].track_number") instanceof Integer);
			logger.debug("items["+i+"].track_number asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].type") instanceof String);
			logger.debug("items["+i+"].type asserted...");
			
			assertTrue(response.jsonPath().get("items["+i+"].uri") instanceof String);
			logger.debug("items["+i+"].uri asserted...");
		}
	
		logger.debug("TEST PASSED...");
	}
	
	@Test 
	public void
	get_an_album_tracks_check_error_code_error_message_with_invalid_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_album_tracks_check_error_code_error_message_with_invalid_id():");
		
		Common.check_error_code_error_message_with_invalid_id_in_path_param("albums/{id}/tracks",requestSpecs);
		logger.debug("TEST PASSED...");
	}

	@Test 
	public void
	get_an_album_tracks_check_error_code_error_message_with_negative_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_album_tracks_check_error_code_error_message_with_negative_id():");
		
		Common.check_error_code_error_message_with_negative_id_in_path_param("albums/{id}/tracks", requestSpecs);
		logger.debug("TEST PASSED...");
	}
	
	@Test
	public void get_an_album_tracks_check_error_code_error_message_with_missing_id() {
		logger.debug("......................................................................................");
		logger.debug("In test get_an_album_tracks_check_error_code_error_message_with_missing_id():");
		
		Common.check_error_code_error_message_with_missing_id_in_path_param("albums/{id}/tracks",requestSpecs);
		
		logger.debug("TEST PASSED...");
	}
}