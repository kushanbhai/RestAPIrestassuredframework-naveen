package com.qa.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.util.TestBase;
import com.qa.util.TestUtil;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherInfoTests extends TestBase{
	@BeforeMethod
	public void setup() {
		TestBase.init();
	}
@DataProvider
public Object[][] getData() {
	Object[][] testData = TestUtil.getTestData(TestUtil.WeathersheetName);
	return testData;
}
	@Test(dataProvider = "getData")
	public void getweatherdetails(String city, String HTTPMethod,String humidity,String temperature, String windspeed,String winddirectiondegree) {
		//define the base url
		RestAssured.baseURI = prop.getProperty("serviceurl");
		//DEFINE THE http request
		RequestSpecification httprequest = RestAssured.given();
		//make the request
		Response resp = httprequest.request(Method.GET, "/"+city);
		//get the response body
		String responsebody = resp.getBody().asString();	
		System.out.println("response body is: "+responsebody);
		//get the status code and validate it
		int statuscode = resp.getStatusCode();
		System.out.println("The status code is: "+statuscode);
		
		//validate it 
		Assert.assertEquals(statuscode, TestUtil.RESPONSE_CODE_200);
		
		//get status line
		System.out.println("The status line is: "+resp.getStatusLine());
		//get the headers
		Headers headers = resp.getHeaders();
		System.out.println(headers);
		//get the specific header value
		String contentType = resp.getHeader("Content-Type");
		System.out.println("The value of the content type is: "+contentType);
		
		String contentlength = resp.getHeader("Content-Length");
		System.out.println("The value of the content length is: "+contentlength);
		
		//get the key value using jsonpath
		JsonPath jsonpathvalue = resp.jsonPath();
		Object cityVal = jsonpathvalue.get("City");
		System.out.println("The value of city is: "+cityVal);
		Assert.assertEquals(cityVal, city);
		
		
		JsonPath jsonpathvaluetemp = resp.jsonPath();
		Object tempval = jsonpathvaluetemp.get("Temperature");
		System.out.println("The value of temperature is: "+tempval);
		Assert.assertEquals(tempval, temperature);
	}
	
}
