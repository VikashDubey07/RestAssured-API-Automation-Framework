package com.qa.api.products.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.ProductPOJO;

import com.qa.api.utilities.JsonDeserializationUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITest extends BaseTest {
	
	@Test
	public void getAllProductTest()
	{
		Response response = restClient.get(PRODUCTS_BASE_URL, PRODUCTS_END_POINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
		
		ProductPOJO [] product = JsonDeserializationUtil.deserialize(response, ProductPOJO [].class);
		
		for (ProductPOJO p : product) 
		{
		    System.out.println("id: " + p.getId());
		    System.out.println("title: " + p.getTitle());
		    System.out.println("price: " + p.getPrice());
		    System.out.println("description: " + p.getDescription());
		    System.out.println("image: " + p.getImage());
		    System.out.println("category: " + p.getCategory());

		    System.out.println("rate: " + p.getRating().getRate());
		    System.out.println("count: " + p.getRating().getCount());
		    System.out.println("**************************************************");
		}
		
	}

}
