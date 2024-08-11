package OAuthAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import Utility.PropertyReader;
public class OAuthAPITest {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String oAuthCredentialsFilePath="src\\main\\resources\\Credentials\\OAuthTestCredentials.properties";
		
		// Grant OAuth Token
		String oAuthTokenResonse=given()
			.header("Content-Type","multipart/form-data; boundary=<calculated when request is sent>")

			.multiPart("client_id",PropertyReader.getProperty(oAuthCredentialsFilePath,"client_id") )
			.multiPart("client_secret", PropertyReader.getProperty(oAuthCredentialsFilePath,"client_secret"))
			.multiPart("grant_type",PropertyReader.getProperty(oAuthCredentialsFilePath,"grant_type"))
			.multiPart("scope",PropertyReader.getProperty(oAuthCredentialsFilePath,"scope"))
			.log().all()
		.post("oauthapi/oauth2/resourceOwner/token")
		.then()
			.log().all()
			.assertThat()
			.statusCode(200)
			.extract().response().asString();
		
		JsonPath js1=new JsonPath(oAuthTokenResonse);
		String oAuthToken=js1.getString("access_token");
		System.out.println("OAuth token granted : "+oAuthToken);
		
		//Get Call using OAuth token
		String getResponse=given()
			.queryParam("access_token", oAuthToken)
		.get("oauthapi/getCourseDetails")
		.then()
			.log().all()
			.extract().response().asString();
		JsonPath js2=new JsonPath(getResponse);
		String courses=js2.getString("courses");
		System.out.println("Fetched courses are : "+courses);
	}

}
