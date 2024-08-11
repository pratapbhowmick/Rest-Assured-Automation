package OAuthAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import POJO.Api;
import POJO.GetCourse;
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
		
//		//Get Call using OAuth token
//		String getResponse=given()
//			.queryParam("access_token", oAuthToken)
//		.get("oauthapi/getCourseDetails")
//		.then()
//			.log().all()
//			.extract().response().asString();
//		JsonPath js2=new JsonPath(getResponse);
//		String courses=js2.getString("courses");
//		System.out.println("Fetched courses are : "+courses);
		
		
		//POJO class
				GetCourse getCourse=given()
					.queryParam("access_token", oAuthToken)
				.get("oauthapi/getCourseDetails")
				.as(GetCourse.class);
				
				System.out.println("LinkedIn Link : "+getCourse.getLinkedIn());
				System.out.println("Instructor name : "+getCourse.getInstructor());
//				System.out.println("Available API course : "+getCourse.getCourses().getApi().get(0).getCourseTitle());
//				System.out.println("Course Price: "+getCourse.getCourses().getApi().get(0).getPrice());
				List<Api> ApiCourses= getCourse.getCourses().getApi();
				for (Api api : ApiCourses) {
					System.out.println("Course Title : "+api.getCourseTitle());
					System.out.println("Price : "+api.getPrice());
					System.out.println("-----------------");
				}
				
				//Print Json
				
				ObjectMapper mapper=new ObjectMapper();
				String JSONResponse=mapper.writeValueAsString(getCourse);
				System.out.println(JSONResponse);
				
	}

}
