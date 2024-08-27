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

public class TC_API_Login {
	private String account;
	private String password;
	private Response response;
	private ResponseBody resBody;
	private JsonPath jsonBody;
	
	@BeforeClass
	public void init() {
		String URL=PropertiesFileUtils.getProperty("baseurl");
		String loginPath=PropertiesFileUtils.getProperty("loginPath");
		
		
		password=PropertiesFileUtils.getProperty("password");
		account=PropertiesFileUtils.getProperty("account");
		
		RestAssured.baseURI=URL;
		
		Map<String, Object> body= new HashMap<String,Object>();				//HashMap về cơ bản là chỉ định các unique key cho các value 
		body.put("account",account);
		body.put("password", password);
		
        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body);
		response = request.post(loginPath);
		resBody = response.body();
		jsonBody = resBody.jsonPath();
		System.out.println(" " + resBody.asPrettyString());
	}
	
	@Test(priority=0)
	public void T01_Validate200_OK() {
		assertEquals(response.getStatusCode(),200,"Status code check failed!");
	}
	@Test(priority=1)
	public void T02_ValidateMessage() {
		System.out.println(response);
		assertTrue(resBody.asString().contains("message"),"Message field check failed!");
		assertEquals(jsonBody.getString("message"),"Đăng nhập thành công","Message is not Đăng nhập thành công!" );
	}
	@Test(priority=2)
	public void T03_ValidateToken() {
		assertTrue(resBody.asString().contains("token"),"Token check failed!");
		String token=jsonBody.get("token");
		PropertiesFileUtils.saveToken("token",token);
	}
	@Test(priority=3)
	public void T04_ValidateUserType() {
		assertTrue(response.asString().contains("type"), "Type check failed!");
		assertEquals(jsonBody.getString("user.type"),"UNGVIEN","Type of user is not UNGVIEN");
	}
	@Test(priority=4)
	public void T05_ValidateAccount() {
		assertTrue(response.asString().contains("account"),"Account check failed!");
		assertEquals(jsonBody.getString("user.account"),account,"Account is not match!");
	}
	@Test(priority=5)
	public void T06_ValidatePassword() {
		assertTrue(response.asString().contains("password"),"Password check failed!");
		assertEquals(jsonBody.getString("user.password"),password,"Password is not match!");
	}
	
	@AfterClass																		// có ý nghĩa lúc chạy test tiếp theo khác url
	public void afterTest() {
		RestAssured.baseURI=null;
	}
}
