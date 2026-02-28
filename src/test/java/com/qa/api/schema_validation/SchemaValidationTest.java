package com.qa.api.schema_validation;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.GoRestUserPOJO;
import com.qa.api.utilities.SchemaValidatorUtil;
import com.qa.api.utilities.StringUtil;

import java.io.File;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class SchemaValidationTest extends BaseTest {

    @Test
    public void getUsersAPISchemaTest() {
        RestAssured.baseURI = "https://gorest.co.in";

        RestAssured.given()
                .header("Authorization", "Bearer 6613a2b16fae0a48461507d7ef772b7acf5bf20310f83f03eda4499e4e8c018b")
                .when()
                .get("/public/v2/users")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body(matchesJsonSchemaInClasspath("Schemas/getUserSchema.json"));//Pass Stracture of JSON that means Json Schema file that needs to be validated
    }

    @Test
    public void createUserAPISchemaTest() 
    {
        RestAssured.baseURI = "https://gorest.co.in";

        RestAssured.given()
                .header("Authorization", "Bearer 6613a2b16fae0a48461507d7ef772b7acf5bf20310f83f03eda4499e4e8c018b")
                .body(new File("./src/test/resources/JSON/gorestUser.json"))//Pass the Actula JSON Paylaod without duplication of field value
                .contentType(ContentType.JSON)
                .when()
                .post("/public/v2/users")
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .body(matchesJsonSchemaInClasspath("Schemas/createSchema.json"));// Pass Stracture of JSON that means Json Schema file that needs to be validated
    }
	
    
    //The above Two Test Methods are written in normal core way
    //lets write the above two tests with utilities
    @Test
    public void getUserAPISchemaTestUsingUtil() 
    {
    	Response resposne  = restClient.get(GOREST_BASE_URL, GOREST_END_POINT, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
    
    	Assert.assertTrue(SchemaValidatorUtil.validateSchema(resposne , "Schemas/getUserSchema.json"));//schema file is inside Schemas Folder
    }
    
    @Test
    public void creaeteUserAPISchemaTestUsingUtil() 
    {
    	
    	GoRestUserPOJO user = GoRestUserPOJO.builder()
    						  .name("Vikas")
    						  .email(StringUtil.generateEmail())
    						  .gender("male")
    						  .status("active")
    						  .build();
    	
    	Response resposne  = restClient.post(GOREST_BASE_URL, GOREST_END_POINT, user,null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
    
    	Assert.assertTrue(SchemaValidatorUtil.validateSchema(resposne , "Schemas/createSchema.json"));//schema file is inside Schemas Folder
    }
    
    
}
