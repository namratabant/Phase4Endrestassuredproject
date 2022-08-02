package restassured;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEnd {
	
	
	
	
	

		Response response;
		String BaseURI =  "http://3.84.11.76:8088/employees";
		@Test
		public void test1()
		{
			// to fetch all the employee details
			response= GetMethodAll();
			Assert.assertEquals(response.getStatusCode(), 200);
			// to update the data
			response=PostMethod("namrata", "ramraj","2000","eee@gmail.com");
			JsonPath Jpath =response.jsonPath();
			int EmpId=Jpath.get("id");
			System.out.println("id"+ EmpId);


			Assert.assertEquals(response.getStatusCode(), 201);

			response=PutMethod(EmpId,"tom","US","6000","nam@gmail.com");

			Assert.assertEquals(response.getStatusCode(), 200);
			Jpath=response.jsonPath();
			Assert.assertEquals(Jpath.get("firstName"), "tom");

			//response=Delete(EmpId);
			//Assert.assertEquals(response.getStatusCode(), 200);
			/*String ResponseBody = response.getBody().asString();
	        Assert.assertEquals(ResponseBody, "{}"); */
			//Assert.assertEquals(response.getBody().asString(), "{}");

			//response=GetMethod(EmpId);
			//Assert.assertEquals(response.getStatusCode(), 400);






		}

		public Response GetMethodAll()
		{
			RestAssured.baseURI = BaseURI;
			RequestSpecification request =RestAssured.given();
			Response response=request.get();

			// printing

			String responsebody=response.getBody().asString();
			System.out.println(responsebody);
			return response;
		}

		public Response GetMethod(int empid)
		{
			RestAssured.baseURI = BaseURI;
			RequestSpecification request =RestAssured.given();
			Response response=request.get("/"+empid);

			// printing

			String responsebody=response.getBody().asString();
			System.out.println(responsebody);
			return response;
		}

		public Response PostMethod(String firstname, String lastname,String salary, String Emaild)
		{
			RestAssured.baseURI = BaseURI;

			RequestSpecification request =RestAssured.given();

			JSONObject jobj=new JSONObject();
			jobj.put("firstName", firstname);
			jobj.put("lastName", lastname);
			jobj.put("salary", salary);
			jobj.put("email", Emaild);


			Response  resp=request.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.body(jobj.toString())
					.post();

			return resp;

		}


		public Response PutMethod(int Empid, String firstname, String lastname,String salary, String Emaild)
		{
			RestAssured.baseURI = BaseURI;

			RequestSpecification request =RestAssured.given();

			JSONObject jobj=new JSONObject();
			jobj.put("firstName", firstname);
			jobj.put("lastName", lastname);
			jobj.put("salary", salary);
			jobj.put("email", Emaild);


			Response  resp=request.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.body(jobj.toString())
					.put("/"+Empid);

			return resp;

		}

		public Response Delete(int Empid)
		{
			RestAssured.baseURI = BaseURI;

			RequestSpecification request =RestAssured.given();

			Response response=request.delete("/"+Empid);
			return response;

			/*String responsebody=response.getBody().asString();
		System.out.println(responsebody);
		int responsecode=response.getStatusCode();
		System.out.println(response.getStatusCode());
		Assert.assertEquals(responsecode, 200);*/
		}
	}

