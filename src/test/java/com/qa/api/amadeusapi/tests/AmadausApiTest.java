package com.qa.api.amadeusapi.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.configmanager.ConfigReaderManager;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AmadausApiTest extends BaseTest{
	
	
	private String accessToken;

	@BeforeMethod
	public void getOAuth2Token() {

	    Response response = restClient.post(
	    		AMADEUS_OAUTH2_BASE_URL,
	    		AMADEUS_OAUTH2_END_POINT,
	    		ConfigReaderManager.get("clientid"),
	    		ConfigReaderManager.get("clientsecret"),
	    		ConfigReaderManager.get("granttype"),
	    		ContentType.URLENC
	    );

	    accessToken = response.jsonPath().getString("access_token");
	    System.out.println("Access Token: " + accessToken);
	    ConfigReaderManager.set("bearerToken", accessToken);
	}

	@Test
	public void getFlightDetailsTest() {

	    // https://test.api.amadeus.com/v1/shopping/flight-destinations?origin=PAR&maxPrice=200
	    // /Maps.of("origin", "PAR", "maxPrice", "200");

	    Map<String, String> queryParams = new HashMap<String, String>();
	    queryParams.put("origin", "PAR");
	    queryParams.put("maxPrice", "200");

	    Response response = restClient.get(AMADEUS_OAUTH2_BASE_URL,AMADEUS_FLIGHT_DESTINATION_END_POINT,queryParams,null,AuthType.BEARER_TOKEN,ContentType.ANY);

	    Assert.assertEquals(response.getStatusCode(), 200);
	}

}
