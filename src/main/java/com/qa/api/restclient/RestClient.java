package com.qa.api.restclient;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.configmanager.ConfigReaderManager;
import com.qa.api.constants.AuthType;
import com.qa.api.exception.ApiException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.Base64;
import java.util.Map;

public class RestClient {
	
	
	private ResponseSpecification  responseSpec200 = expect().statusCode(200);
	private ResponseSpecification  responseSpec201 = expect().statusCode(201);
	private ResponseSpecification  responseSpec204 = expect().statusCode(204);
	private ResponseSpecification  responseSpec400 = expect().statusCode(400);
	private ResponseSpecification  responseSpec401 = expect().statusCode(401);
	private ResponseSpecification  responseSpec404 = expect().statusCode(404);
	private ResponseSpecification  responseSpec200or201 = expect().statusCode(anyOf(equalTo(200),equalTo(201)));
	private ResponseSpecification  responseSpec200or404 = expect().statusCode(anyOf(equalTo(200),equalTo(404)));
	
	
	
	private RequestSpecification setUpRequest(String baseURL, AuthType authType, ContentType contentType)
	{
		
		ChainTestListener.log("API base url: " + baseURL);
		ChainTestListener.log("Auth Type : " + authType.toString());
		
		RequestSpecification request = RestAssured.given().log().all()
	
				.baseUri(baseURL)
				.contentType(contentType)
				.accept(contentType);
		
		switch(authType)
		{
		
		case BEARER_TOKEN:
			request.header("Authorization","Bearer "+ConfigReaderManager.get("bearerToken"));//reading bearer token from configReaderManager class
			break;
		case OAUTH2:
			request.header("Authorization","Bearer"+"Some Oath2 Token");
			break;
		case BASIC_AUTH:
			request.header("Authorization","Basic "+generateBasicAuthToken());
			break;
		case API_KEY:
			request.header("x-api-key","api key Value");
			break;
		case NO_AUTH:
			System.out.println("Authenication is not required for this API");
			break;
		default:
			System.out.println("Wrong Auth Type: Please pass the Correct Authenication Type ");
			throw new ApiException("=====Invalid Authenication Type");
		
		}
		return request;	
	}
	
	
	
	private void applyParams(RequestSpecification request, Map<String, String> queryParams, Map<String, String> pathParams)
	{
		
		ChainTestListener.log("queryParams: " + queryParams);
		ChainTestListener.log("pathParams: " + pathParams);
		
		if(queryParams!=null)
		{
			request.queryParams(queryParams);
		}
		
		if(pathParams!=null)
		{
			request.pathParams(pathParams);
		}
	}
	
	
	private String generateBasicAuthToken()
	{
		String credentials = ConfigReaderManager.get("BasicAuthUserName")+":"+ConfigReaderManager.get("BasicAuthPassword");
		return Base64.getEncoder().encodeToString(credentials.getBytes());
	}
	
	
	
	
	
	
	//CRUD Operations 
	//GET() Call
	
	
	/**
	 * This Method is used to call the GET API's
	 * @param baseURL
	 * @param endPointUrl
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return the GET API call Response
	 */
	public Response get(String baseURL, String endPointUrl, Map<String, String> queryParams, Map<String, String> pathParams,AuthType authType, ContentType contentType)
	{
		RequestSpecification request = setUpRequest( baseURL, authType, contentType);
		applyParams(request,queryParams,pathParams);
		
	    Response response = request.get(endPointUrl)                                           //Function returning Response
									.then()
									.spec(responseSpec200or404)
									.extract()
									.response();
	    response.prettyPrint();
	    return response;
	
	
	}
	
	
	//POST CALL
	
	public <T> Response post(String baseURL, String endPointUrl, T body, Map<String, String> queryParams, Map<String, String> pathParams,AuthType authType, ContentType contentType)
	{
		RequestSpecification request = setUpRequest( baseURL, authType, contentType);
		applyParams(request,queryParams,pathParams);
		
	    Response response = request.body(body).post(endPointUrl)                                           //Function returning Response
									.then()
									.spec(responseSpec200or201)
									.extract()
									.response();
	    response.prettyPrint();
	    return response;
	}
	
	
	//POST Call with JSON File Payload
	
	public Response post(String baseURL, String endPointUrl, File file, Map<String, String> queryParams, Map<String, String> pathParams,AuthType authType, ContentType contentType)
	{
		RequestSpecification request = setUpRequest( baseURL, authType, contentType);
		applyParams(request,queryParams,pathParams);
		
	    Response response = request.body(file).post(endPointUrl)                                           //Function returning Response
									.then()
									.spec(responseSpec200or201)
									.extract()
									.response();
	    response.prettyPrint();
	    return response;
	
	}
	
	
	
	//POST call for OAuth2 where client id and client secret and grant type is required
	
	public Response post(String baseUrl, String endPoint, String clientId, String clientSecret, String grantType,
			ContentType contentType) {

		Response response = RestAssured.given()
				.contentType(contentType)
				.formParam("grant_type", grantType)
				.formParam("client_id", clientId)
				.formParam("client_secret", clientSecret)
				.when()
				.post(baseUrl + endPoint);

		response.prettyPrint();
		return response;
}
	
	
	
	//PUT Call
	
	public <T> Response put(String baseURL, String endPointUrl, T body, Map<String, String> queryParams, Map<String, String> pathParams,AuthType authType, ContentType contentType)
	{
		RequestSpecification request = setUpRequest( baseURL, authType, contentType);
		applyParams(request,queryParams,pathParams);
		
	    Response response = request.body(body).put(endPointUrl)                                           //Function returning Response
									.then()
									.spec(responseSpec200)
									.extract()
									.response();
	    response.prettyPrint();
	    return response;
	}
	
	
	//Patch Call
	
	public <T> Response patch(String baseURL, String endPointUrl, T body, Map<String, String> queryParams, Map<String, String> pathParams,AuthType authType, ContentType contentType)
	{
		RequestSpecification request = setUpRequest( baseURL, authType, contentType);
		applyParams(request,queryParams,pathParams);
		
	    Response response = request.body(body).patch(endPointUrl)                                           //Function returning Response
									.then()
									.spec(responseSpec200)
									.extract()
									.response();
	    response.prettyPrint();
	    return response;
	}
	
	
	//DELETE Call
	
	public Response delete(String baseURL, String endPointUrl, Map<String, String> queryParams, Map<String, String> pathParams,AuthType authType, ContentType contentType)
	{
		RequestSpecification request = setUpRequest( baseURL, authType, contentType);
		applyParams(request,queryParams,pathParams);
		
	    Response response = request.delete(endPointUrl)                                           //Function returning Response
									.then()
									.spec(responseSpec204)
									.extract()
									.response();
	    response.prettyPrint();
	    return response;
	
	
	}
	
}
