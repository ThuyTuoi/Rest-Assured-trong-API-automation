package com.api.auto.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.auto.utils.PropertiesFileUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class TC_API_CreateWork {
	private String token;
	private Response response;
	private ResponseBody resBody;
	private JsonPath jsonBody;
	private String nameWork="Software tester";
	private String experience= "Manual tester";
	private String education= "Electronics";
	
	@BeforeClass
	
	public void init() {
		token=PropertiesFileUtils.getToken("token");
		String base_URL= PropertiesFileUtils.getProperty("baseurl");
		String createWorkPath=PropertiesFileUtils.getProperty("createWorkPath");
		RestAssured.baseURI=base_URL;
				
		Map<String, Object> body=new HashMap<String, Object>();
		body.put("nameWork",nameWork);
		body.put("experience",experience);
		body.put("education",education);
		
		RequestSpecification request =RestAssured.given()
			.contentType(ContentType.JSON)
			.header("token",token)
			.body(body);
		
		response=request.post(createWorkPath);
		resBody=response.body();
		jsonBody=resBody.jsonPath();
		System.out.println(" "+resBody.asPrettyString());
	}
	
	@Test(priority=0)
	public void Tc01_Validate201Created() {
		assertEquals(response.getStatusCode(),201,"Status code check successfully!");
	}
	@Test(priority=1)
	public void Tc02_ValidateWorkId() {
		assertTrue(response.asString().contains("id"), "Id check failed!");
	}
	@Test(priority=2)
	public void Tc03_ValidateNameOfWorkMatched() {
		assertTrue(response.asString().contains("nameWork"),"NameWork check failed!");
		assertEquals(jsonBody.get("nameWork"),nameWork,"NameWork is not match!");
	}
	@Test(priority=3)
	public void Tc04_ValidateExperienceMatched() {
		assertTrue(response.asString().contains("experience"),"Experience check failed!");
		assertEquals(jsonBody.get("experience"),experience,"Experience is not match!");
	}
	@Test(priority=4)
	public void Tc05_ValidateEducationMatched() {
		assertTrue(response.asString().contains("education"),"Education check failed!");
		assertEquals(jsonBody.get("education"),education,"Education is not match!");
	}
	@AfterClass
	
	public void aftertest() {
		RestAssured.baseURI=null;
	}
	
}
