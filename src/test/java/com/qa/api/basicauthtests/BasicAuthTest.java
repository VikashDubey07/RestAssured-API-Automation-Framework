package com.qa.api.basicauthtests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BasicAuthTest extends BaseTest{
	
	
	@Test
	public void basicAuthTest()
	{
		Response response = restClient.get(BASIC_AUTH_BASE_URL, BASIC_AUTH_END_POINT, null, null, AuthType.BASIC_AUTH, ContentType.ANY);
		Assert.assertTrue(response.getBody().asString().contains("Created by Kristin Jackvony"));
	}

}
