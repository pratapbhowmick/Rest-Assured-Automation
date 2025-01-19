package TestPractice;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;
public class GetAndPost {
	@Test(enabled =  false)
	public void testGet() {
		baseURI="https://reqres.in/api";
		GetResponsePOJO getResponse =given()
			.get("/users?page=2")
		.then()
			.statusCode(200)
			.body("total", equalTo(12))
			.body("total_pages", equalTo(2))
			.body("data[5].first_name",equalTo("Rachel"))
			.log().all().extract().response()
			.as(GetResponsePOJO.class);
		
		
		System.out.println("response: "+getResponse.toString()) ;
		System.out.println("first data : "+getResponse.getData().get(0).getId());
//		System.out.println("Response : "+getResponse);
//		JsonPath getJsonPath=new JsonPath(getResponse);
//		System.out.println(getJsonPath.getString("data[5].first_name"));
		
	}
	@Test(enabled = true)
	public void testPost() {
//		JSONObject object=new JSONObject();
//		object.put("name", "Pratap");
//		object.put("name1", "Pratap1");
//		
		PostRequestPOJO postRequest=new PostRequestPOJO("Pratap","Pratap1");
		
		baseURI="https://reqres.in/api";
		PostResonsePOJO postResponse= given()
			.header("Content-Type","application/json")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(postRequest)
			.log().all()
		.when()
			.post("/users")
		.then()
			.statusCode(201)
			.body("name", equalTo("Pratap"))
			.log().all()
			.extract().response().as(PostResonsePOJO.class);
		System.out.println("Response : "+postResponse.toString());
	}
}
