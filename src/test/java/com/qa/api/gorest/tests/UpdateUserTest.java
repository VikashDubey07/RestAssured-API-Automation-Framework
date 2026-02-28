package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.GoRestUserPOJO;
import com.qa.api.utilities.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest {
	
	
	@Test
	public void updateUserTest()
	{
		//Normal Way of Using POJO
		//GoRestUserPOJO user = new GoRestUserPOJO("vikas",StringUtil.generateEmail(),"male","active");
		
		//Lets use Builder Pattern for POJO
		//1.POST: Create User 
		
		GoRestUserPOJO user = GoRestUserPOJO.builder()
				.name("vikas")
				.email(StringUtil.generateEmail())
				.gender("male")
				.status("active")
				.build(); //Do not forget to write Build
		
		Response postResponse = restClient.post(GOREST_BASE_URL, GOREST_END_POINT, user, null, null,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(postResponse.jsonPath().getString("name"),"vikas");
		Assert.assertNotNull(postResponse.jsonPath().getString("id"));
		
		//fetch the user id
		String userId = postResponse.jsonPath().getString("id");
		System.out.println("User Id of Created user during Post call is = " +userId);
		
		
		//2.GET Call : fetch the user using same userId to check if user is created
		
		Response getResponseWithUserId = restClient.get(GOREST_BASE_URL, GOREST_END_POINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(getResponseWithUserId.statusLine().contains("OK"));
		Assert.assertEquals(getResponseWithUserId.jsonPath().getString("id"), userId );
		
		//Update the user using same userID
		//lets update only two fields like name and status
		//3. PUT Call :
		
		user.setName("vijay");
		user.setStatus("inactive");
		Response putResponse = restClient.put(GOREST_BASE_URL, GOREST_END_POINT+"/"+userId, user,null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(putResponse.statusLine().contains("OK"));
		Assert.assertEquals(putResponse.jsonPath().getString("name"), "vijay" );
		Assert.assertEquals(putResponse.jsonPath().getString("status"), "inactive");
		
		//4 GET call: to check again if the user is Updated
		
		Response getUpadatdResponse = restClient.get(GOREST_BASE_URL, GOREST_END_POINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(getUpadatdResponse.statusLine().contains("OK"));
		Assert.assertEquals(getUpadatdResponse.jsonPath().getString("id"), userId );
		Assert.assertEquals(putResponse.jsonPath().getString("name"), "vijay" );
		Assert.assertEquals(putResponse.jsonPath().getString("status"), "inactive");
		
		
	}
	

}
