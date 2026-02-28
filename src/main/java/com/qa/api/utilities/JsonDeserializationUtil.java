package com.qa.api.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class JsonDeserializationUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static <T> T deserialize(Response response, Class<T> targetClass) 
	{
		try 
		{
			return mapper.readValue(response.getBody().asString(),targetClass);
		}
		catch(Exception e)
		{
			throw new RuntimeException("Deserialization is Failed........."+targetClass.getName());
		}
		
		
	}
	

}
