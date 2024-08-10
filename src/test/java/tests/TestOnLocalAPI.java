package tests;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
public class TestOnLocalAPI {
	@Test
	public void get() {
		baseURI="http://localhost:3000";
		given().
			get("users")
		.then()
			.statusCode(200)
			.log().all();
	}
	@Test(priority = 0)
	public void post() {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("firstname","Thomas");
		map.put("lastname","Edison");
		map.put("subjectID", 1);
		JSONObject request=new JSONObject(map);
		baseURI="http://localhost:3000";
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/users")
		.then()
			.statusCode(201);
	}
	@Test(priority = 1)
	public void put() {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("firstname","Albert");
		map.put("lastname","Einstein");
		map.put("subjectID", 2);
		JSONObject request=new JSONObject(map);
		baseURI="http://localhost:3000";
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.put("/users/4")
		.then()
			.statusCode(200);
	}
	@Test(priority = 2)
	public void patch() {
		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("lastname","Doe");
		
		JSONObject request=new JSONObject(map);
		baseURI="http://localhost:3000";
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.patch("/users/4")
		.then()
			.statusCode(200);
	}
	@Test(priority = 3)
	public void delete() {
		
		baseURI="http://localhost:3000";
		
		when()
			.delete("/users/4")
		.then()
			.statusCode(200);
	}
}
