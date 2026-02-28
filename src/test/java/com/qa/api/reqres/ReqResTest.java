package com.qa.api.reqres;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ReqResTest extends BaseTest {
	
	@Test
	public void getUser()
	{
		Map<String, String> queryMap = new HashMap<String,String>();
		{
			queryMap.put("page", "2");
		}
		
		Response response = restClient.get(REQRES_BASE_URL, REQRES_END_POINT, queryMap, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	

}
