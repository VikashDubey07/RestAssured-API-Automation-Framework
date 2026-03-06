package com.qa.api.mocktests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.mocking.ApiMocks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MockGetUserTest extends BaseTest  {
	
	@Test
	public void getDummyUserMockAPITest() 
	{
	    ApiMocks.defineGetUserMock();

	    Response response = 
	        restClient.get(MOCK_SERVER_BASE_URL, "/api/users", null, null, AuthType.NO_AUTH, ContentType.ANY);

	    response.prettyPrint();
	    response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void getDummyUserMockAPIWithQueryParamTest() {

	    ApiMocks.defineGetUserMockWithQueryParam();

	    Map<String, String> userQueryMap = new HashMap<String, String>();
	    userQueryMap.put("name", "Tom");

	    Response response =
	            restClient.get(MOCK_SERVER_BASE_URL, "/api/users",
	                    userQueryMap, null, AuthType.NO_AUTH, ContentType.ANY);

	    response.then().assertThat().statusCode(200);
	}
	

}
