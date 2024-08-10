import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


import static org.hamcrest.Matchers.*;

public class Basics {

	public static void main(String[] args) {
		/* Create Place */
		
		//Post Request
		RestAssured.baseURI="https://rahulshettyacademy.com";	
		String response= given()
			.log().all()
			.queryParam("key","qaclick123")
			.header("content-type","application/json")
			.body(Payload.addPlace())
		.when()
			.post("maps/api/place/add/json")
		.then()
			.log().all()
			.assertThat()
			.statusCode(200)
			.body("scope",equalTo("APP"))
			.header("Server", "Apache/2.4.52 (Ubuntu)")	
		.extract().response().asString()
		;
		//System.out.println(response);
		
		JsonPath js=new JsonPath(response);//to parse JSON
		String place_id= js.getString("place_id");
		System.out.println(place_id);
		
		
		/* Update Place */
		String newAddress="70 Pratap winter walk, USA";
		given()
			.log().all()
			.queryParam("key", "qaclick123")
			.header("Content-Type","application/json")
			.body("{\r\n"
					+ "\"place_id\":\""+place_id+"\",\r\n"
					+ "\"address\":\""+newAddress+"\",\r\n"
					+ "\"key\":\"qaclick123\"\r\n"
					+ "}")
		.when()
			.put("maps/api/place/update/json")
		.then()
			.log().all()
			.assertThat()
			.statusCode(200)
			.body("msg", equalTo("Address successfully updated"))
			;
		
		
		/* Get Place */
		
		String getPlaceResponse= given()
			.log().all()
			.queryParam("key", "qaclick123")
			.queryParam("place_id", place_id)
		.when()
			.get("maps/api/place/get/json")
		.then()
			.log().all()
			.assertThat()
			.statusCode(200)
			.body("address", equalTo(newAddress))
			
		.extract().response().asString()
		;
		js=new JsonPath(getPlaceResponse);
		String actualAddress=js.getString("address");
		System.out.println(actualAddress);
		
	}

}
