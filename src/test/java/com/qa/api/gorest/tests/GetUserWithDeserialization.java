package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.configmanager.ConfigReaderManager;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.GoRestUserPOJO;
import com.qa.api.utilities.JsonDeserializationUtil;
import com.qa.api.utilities.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserWithDeserialization extends BaseTest{
	
	
	private String tokenId;

	@BeforeClass
	public void setUpToken() 
	{
	    tokenId = "e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6";
	    ConfigReaderManager.set("bearertoken", tokenId);
	}
	
	
	//POST
	@Test
	public void getSingleUSer() //For getting user, we need to create theuser firts then get it using id
	{
		
		GoRestUserPOJO user = new GoRestUserPOJO(null,"vikas",StringUtil.generateEmail(),"male","active");//id as null coz id will be at run time at server 
		Response postResponse = restClient.post(GOREST_BASE_URL, GOREST_END_POINT, user, null, null,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(postResponse.jsonPath().getString("name"),"vikas");
		Assert.assertNotNull(postResponse.jsonPath().getString("id"));
		
		//fetch id of created user
		String userID =postResponse.jsonPath().getString("id");
		
		//GET the user with ID
		Response getResponse = restClient.get(GOREST_BASE_URL, GOREST_END_POINT+"/"+userID, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(getResponse.statusLine().contains("OK"));
		Assert.assertEquals(getResponse.jsonPath().getString("id"), userID);
		
		GoRestUserPOJO deserilizeResponse = JsonDeserializationUtil.deserialize(getResponse, GoRestUserPOJO.class);
		
		Assert.assertEquals(deserilizeResponse.getName(), user.getName());
		
	}


	
}
