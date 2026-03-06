package com.qa.api.base;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.configmanager.ConfigReaderManager;
import com.qa.api.mocking.WireMockSetup;
import com.qa.api.restclient.RestClient;

import io.restassured.RestAssured;
import io.qameta.allure.restassured.AllureRestAssured;

//@Listeners(ChainTestListener.class)
public class BaseTest {
	
	
	
	//**********************  Maintain Base URL ****************************//
	
	protected  static String GOREST_BASE_URL_DevEnv = ""; //Variable to read configuration from config_qa.properties
	
	
	protected final static String GOREST_BASE_URL ="https://gorest.co.in";
	protected final static String CONTACTS_BASE_URL ="https://thinking-tester-contact-list.herokuapp.com";
	protected final static String REQRES_BASE_URL ="https://reqres.in";
	protected final static String BASIC_AUTH_BASE_URL ="https://thinking-tester-contact-list.herokuapp.com";
	protected final static String PRODUCTS_BASE_URL ="https://fakestoreapi.com";
	protected final static String AMADEUS_OAUTH2_BASE_URL ="https://test.api.amadeus.com";
	protected final static String MOCK_SERVER_BASE_URL ="http://localhost:8089";

	
	
	
	
	
	
	//**********************  End Points URL****************************//
	
	protected final static String GOREST_END_POINT ="/public/v2/users";
	protected final static String CONATACTS_LOGIN_END_POINT ="/users/login";
	protected final static String CONATACTS_END_POINT ="/contacts";
	protected final static String REQRES_END_POINT ="/api/users";
	protected final static String BASIC_AUTH_END_POINT ="/basic_auth";
	protected final static String PRODUCTS_END_POINT ="/products";
	protected final static String AMADEUS_OAUTH2_END_POINT ="/v1/security/oauth2/token";
	protected final static String AMADEUS_FLIGHT_DESTINATION_END_POINT ="/v1/shopping/flight-destinations";
	
	
	
	
	
	
	//**********************  Maintain one variable at class level for creating object of RestClient class****************************//
	
	protected RestClient restClient;
	
	
	@BeforeSuite
	public void setupAllureReportAndEnvSpecficPropFileReading()
	{
	    RestAssured.filters(new AllureRestAssured());
	    GOREST_BASE_URL_DevEnv = ConfigReaderManager.get("baseUrl").trim(); //To reading configuration from config_qa.properties
	    
	}
	
	@BeforeTest
	public void setUp()
	{
		restClient = new RestClient();
		WireMockSetup.startWireMockeServer();
	}
	
	
	@AfterTest
	public void stopWireMockServer()
	{
		
		WireMockSetup.stopWireMockServer();
	}
	

	
	
	
}
