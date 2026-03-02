package com.qa.api.gorest.tests;

import java.io.File;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.GoRestUserPOJO;
import com.qa.api.utilities.ExcelReader;
import com.qa.api.utilities.StringUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest{
	
	
	
	@DataProvider
	public Object[][] getUserData()
	{
	    return new Object[][] 
	    {
	        {"Priyanka", "female", "active"},
	        {"Ranjit", "male", "inactive"},
	        {"Elam", "male", "active"}
	    };
	}
	
	
	
	
	@DataProvider
	public Object[][] getUserExcelData() throws InvalidFormatException {
	    return ExcelReader.readData(AppConstants.EXCEL_SHEET_NAME);
	}
	
	
	//@Test(dataProvider = "getUserData")
	@Test(dataProvider = "getUserExcelData")
	public void createUser(String name, String gender,String status)
	{
		GoRestUserPOJO user = new GoRestUserPOJO(null,name,StringUtil.generateEmail(),gender,status);
		Response response = restClient.post(GOREST_BASE_URL, GOREST_END_POINT, user, null, null,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"),name);
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
		System.out.println("==================================================================================");
		System.out.println("Name = "+name);
		System.out.println("Gender = "+gender);
		System.out.println("Status = "+status);
		
		System.out.println("===================================================================================");
		
	}
	
	
	@Test
	public void createUserWithJsonString()
	{
		
		String email = StringUtil.generateEmail();
		
		String userJsonString = "{\r\n"
				+ "  \"name\": \"Arjun Mehta\",\r\n"
				+ "  \"email\": \""+email+"\",\r\n"
				+ "  \"gender\": \"male\",\r\n"
				+ "  \"status\": \"active\"\r\n"
				+ "}";
		
		Response response = restClient.post(GOREST_BASE_URL, GOREST_END_POINT, userJsonString, null, null,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"),"Arjun Mehta");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
	}
	
	
	@Test(enabled = false)
	public void createUserWithJsonFile()
	{
		File userFile = new File("./src/test/resources/JSON/gorestUser.json");
		Response response = restClient.post(GOREST_BASE_URL, GOREST_END_POINT, userFile, null, null,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"),"Rohit Sharma");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
	}
	
	

}
