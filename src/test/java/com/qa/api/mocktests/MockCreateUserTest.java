package com.qa.api.mocktests;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.mocking.ApiMocks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MockCreateUserTest extends BaseTest {
	
	@Test
	public void createAFakeUserTest() {

	    ApiMocks.defineCreateUserMock();

	    String dummyUserJson = "{\n"
	            + " \"name\": \"tom\"\n"
	            + "}";

	    Response response = restClient.post(
	    		MOCK_SERVER_BASE_URL,
	            "/api/users",
	            dummyUserJson,
	            null,
	            null,
	            AuthType.NO_AUTH,
	            ContentType.JSON
	    );

	    response.then().assertThat().statusCode(201);
	}

}
