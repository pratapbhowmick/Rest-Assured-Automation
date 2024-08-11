package Serialization;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Arrays;

import io.restassured.RestAssured;

public class MapAPITest {
	public static void main(String[] args) {
		RestAssured.baseURI="https://rahulshettyacademy.com";
//		MapAPIAppPlacePojo addPlace=new MapAPIAppPlacePojo();
//		addPlace.setAccuracy(50);
//		addPlace.setName("Pratap House");
//		addPlace.setPhone_number("(+91) 983 893 3937");
//		addPlace.setAddress("29, side layout, cohen 09");
//		addPlace.setWebsite("http://google.com");
//		addPlace.setLanguage("French-IN");
//		addPlace.setTypes(new ArrayList<String>(Arrays.asList("show park","shop")));
		//Location location=new Location(-38.383494,33.427362);
//		location.setLat( -38.383494);
//		location.setLng(33.427362);
		//addPlace.setLocation(new Location(-38.383494,33.427362));
		
		MapAPIAppPlacePojo addPlace=new MapAPIAppPlacePojo(50, "Pratap House", "(+91) 983 893 3937", "29, side layout, cohen 09",
				new ArrayList<String>(Arrays.asList("show park","shop")), "http://google.com", "French-IN", new Location(-38.383494,33.427362));
		
		String response= given()
			.log().all()
			.queryParam("key","qaclick123")
			.header("content-type","application/json")
			.body(addPlace)
			//.body(Payload.addPlace())
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
	}

}
