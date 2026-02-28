package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.configmanager.ConfigReaderManager;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.ContactCredentialsPOJO;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactsApiTest extends BaseTest{
	
	//Contacts API required login token at first before hitting any API, so we need to have token first before
	//calling any API like GET, POST, PUT, DELETE. we have written @BeforeMethod to get the token by passing email and password
	
	private String tokenId;
	
	
	
	
	@BeforeMethod
	public void getToken()
	{
		
		ContactCredentialsPOJO  credential = ContactCredentialsPOJO.builder()//using Builder pattern for accessing POJO class
				.email("mishra12@nal.com")
				.password("Test@123")
				.build();
				
				
		Response response  = restClient.post(CONTACTS_BASE_URL, CONATACTS_LOGIN_END_POINT, credential, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		tokenId = response.jsonPath().getString("token");
		System.out.println("Token id ="+tokenId);
		Assert.assertEquals(response.statusCode(), 200);
		//update the Token id coz this API has different Token id  so update it dynamically ,as Our Config.prop file has different Token Id
		System.out.println("Contacts Login JWT Token = "+tokenId);
		ConfigReaderManager.set("bearerToken", tokenId);
		
		
		
		
		
	}
	
	
	
	@Test
	public void getAllContactsTest()
	{
		Response response = restClient.get(CONTACTS_BASE_URL, CONATACTS_END_POINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
	}
	

}
