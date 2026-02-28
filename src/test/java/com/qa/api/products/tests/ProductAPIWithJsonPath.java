package com.qa.api.products.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utilities.JsonPathValidatorUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPIWithJsonPath extends BaseTest{
	@Test
	public void getAllProductTest()
	{
		Response response = restClient.get(PRODUCTS_BASE_URL, PRODUCTS_END_POINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
		
		
		List<Number> prices = JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].price");
		
		System.out.println(prices);
		
		List<Number> ids = JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].id");
		System.out.println(ids);

		List<Number> rates = JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].rating.rate");
		System.out.println(rates);

		List<Integer> counts = JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].rating.count");
		System.out.println(counts);
		
		List<Map<String, Object>> idTitleList = JsonPathValidatorUtil.readListOfMaps(response, "$.[*].['id', 'title']");
		System.out.println(idTitleList);

		List<Map<String, Object>> idTitleCatList = JsonPathValidatorUtil.readListOfMaps(response, "$.[*].['id', 'title', 'category']");
		System.out.println(idTitleCatList);

		List<Map<String, Object>> jewlDTitleList = JsonPathValidatorUtil.readListOfMaps(response, "$[?(@.category == 'jewelery')].['id', 'title']");
		System.out.println(jewlDTitleList);
		
		Double price = JsonPathValidatorUtil.read(response, "min($[*].price)");
		System.out.println(price);
	}
	
}
