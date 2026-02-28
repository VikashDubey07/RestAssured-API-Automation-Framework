package com.qa.api.gorest.tests;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserTest extends BaseTest {

	@Test
	public void getAllUserTest()
	{

		Response response = restClient.get(GOREST_BASE_URL, GOREST_END_POINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));

	}

	@Test
	public void getAllUserWithQueryParamTest() 
	{

		HashMap<String, String> queryParameter = new HashMap<String, String>();
		queryParameter.put("name", "naveen");
		queryParameter.put("status", "active");

		Response response = restClient.get(GOREST_BASE_URL, GOREST_END_POINT, queryParameter, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}

	@Test
	public void getSingleUserTest() 
	{
		
		String userId = "8332864";
		Response response = restClient.get(GOREST_BASE_URL, GOREST_END_POINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
		Assert.assertEquals(response.jsonPath().getString("id"), userId );
	}
	
	
	
	
	
}
