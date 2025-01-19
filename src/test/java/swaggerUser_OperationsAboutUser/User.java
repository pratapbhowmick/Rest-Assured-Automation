package swaggerUser_OperationsAboutUser;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class User {
	RequestSpecification req = null;

	@BeforeTest
	public void setup() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri("https://petstore.swagger.io/v2");
		requestSpecBuilder.addHeader("Content-Type", "application/json");
		requestSpecBuilder.setContentType(ContentType.JSON);
		requestSpecBuilder.setAccept(ContentType.JSON);
		req = requestSpecBuilder.build();
	}

	@Test(priority = 0, enabled = true)
	public void createUser() {
		// baseURI = "https://petstore.swagger.io/v2";
		given()
			.spec(req)
//		header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
			.body("{\r\n" + "  \"id\": 234,\r\n" + "  \"username\": \"prataptest\",\r\n"
						+ "  \"firstName\": \"Pratap\",\r\n" + "  \"lastName\": \"Bhowmick\",\r\n"
						+ "  \"email\": \"test@gmail.com\",\r\n" + "  \"password\": \"string\",\r\n"
						+ "  \"phone\": \"9090909090\",\r\n" + "  \"userStatus\": 0\r\n" + "}")
		.when()
			.post("/user")
		.then()
			.statusCode(200)
				.body("message", equalTo("234"))
				.log().all();
	}

	@Test(priority = 1, enabled = true)
	public void getUser() {
		baseURI = "https://petstore.swagger.io/v2";
		given()
			.spec(req)
			.pathParam("username", "prataptest")
		.when()
			.get("/user/{username}")
		.then()
			.statusCode(200)
			.body("email", equalTo("test@gmail.com"))
			.log().all();
	}

	@Test(priority = 2, enabled = true)
	public void updateUser() {
		//baseURI = "https://petstore.swagger.io/v2";
		given()
			.spec(req)
			.pathParam("username", "prataptest")
			.body("{\r\n" + "  \"id\": 234,\r\n" + "  \"username\": \"prataptest\",\r\n"
						+ "  \"firstName\": \"Pratap\",\r\n" + "  \"lastName\": \"Bhowmick\",\r\n"
						+ "  \"email\": \"test123@gmail.com\",\r\n" + "  \"password\": \"string\",\r\n"
						+ "  \"phone\": \"9090909091\",\r\n" + "  \"userStatus\": 0\r\n" + "}")
		.when()
			.put("/user/{username}")
		.then()
			.statusCode(200)
			.body("message", equalTo("234"))
			.log().all();
	}

	@Test(priority = 3, enabled = true)
	public void getUpdatedUser() {
		//baseURI = "https://petstore.swagger.io/v2";
		given()
			.spec(req)
			.pathParam("username", "prataptest")
		.when()
			.get("/user/{username}")
		.then()
			.statusCode(200)
			.body("email", equalTo("test123@gmail.com"))
			.body("phone", equalTo("9090909091"))
			.log().all();
	}

	@Test(priority = 4, enabled = true)
	public void loginUser() {
		//baseURI = "https://petstore.swagger.io/v2";
		given()
			.spec(req)
			.queryParam("username", "prataptest")
			.queryParam("password", "string")
		.when()
			.get("/user/login")
		.then()
			.statusCode(200)
			.body("message", startsWith("logged in user session:"))
			.log().all();
	}

	@Test(priority = 5, enabled = true)
	public void logoutUser() {
		//baseURI = "https://petstore.swagger.io/v2";
		given()
			.spec(req)
		.when()
			.get("/user/logout")
		.then()
			.statusCode(200)
			.body("message", equalTo("ok"))
			.log().all();
	}

	@Test(priority = 6, enabled = true)
	public void deleteUser() {
		//baseURI = "https://petstore.swagger.io/v2";
		given()
			.spec(req)
			.pathParam("username", "prataptest")
		.when()
			.delete("/user/{username}")
		.then()
			// .statusCode(200)
			// .body("message", equalTo("prataptest"))
			.log().all();
	}

	@Test(priority = 7, enabled = true)
	public void getUserAfterDelete() {
		//baseURI = "https://petstore.swagger.io/v2";
		given()
			.spec(req)
			.pathParam("username", "prataptest")
		.when()
			.get("/user/{username}")
		.then()
			.statusCode(404)
			.body("message", equalTo("User not found"))
			.log().all();
	}

}
