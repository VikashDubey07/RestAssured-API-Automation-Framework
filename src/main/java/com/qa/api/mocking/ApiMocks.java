package com.qa.api.mocking;


import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import static com.github.tomakehurst.wiremock.client.WireMock.post;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;


public class ApiMocks {
	
	    //********************Create Mock/Stub for GET CALL********************//

	    public static void defineGetUserMock() {

	        // http://localhost:8089/api/users
	        stubFor(get(urlEqualTo("/api/users"))
	                .willReturn(aResponse()
	                        .withStatus(200)
	                        .withHeader("Content-Type", "application/json")
	                        .withHeader("server-name", "bankserver")
	                        .withBodyFile("mockuser.json")));
	                
//	                        .withBody("{\n"
//	                                + "    \"name\": \"tom\"\n"
//	                                + "}")));
	    }
	    
	    
	    
	    public static void defineGetUserMockWithQueryParam() 
	    {
	        // http://localhost:8089/api/users?name=tom
	        stubFor(get(urlPathEqualTo("/api/users"))
	                .withQueryParam("name", equalTo("Tom"))
	                .willReturn(aResponse()
	                        .withStatus(200)
	                        .withHeader("Content-Type", "application/json")
	                        .withHeader("server-name", "bankserver")
	                        .withBodyFile("mockuser.json")
	                )
	        );
	    }
	    

		public static void defineCreateUserMock() {

	        stubFor(post(urlEqualTo("/api/users"))
	                .withHeader("Content-Type", equalTo("application/json"))
	                .willReturn(aResponse()
	                        .withStatus(201)
	                        .withHeader("Content-Type", "application/json")
	                        .withHeader("server-name", "bankserver")
	                        .withBody("{\n"
	                                + "  \"_id\": 1,\n"
	                                + "  \"name\": \"tom\",\n"
	                                + "  \"age\": 30,\n"
	                                + "  \"salary\": 15.1\n"
	                                + "}")
	                )
	        );
	    }
}

