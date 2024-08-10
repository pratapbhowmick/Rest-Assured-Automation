package JiraAPITest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import Utility.PropertyReader;

//import Utility.PropertyReader;

public class BugTest {
	public static void main(String[] args) throws FileNotFoundException, IOException { // TODO Auto-generated method stub
		RestAssured.baseURI="https://rahulshettyacademy-team.atlassian.net/";
		String AuthorisationToken= PropertyReader.getProperty("AuthorisationToken");
		String createIssueResponse 	= given()		
				.header("Content-Type","application/json")		
				.header("Authorization",AuthorisationToken)		
				.body("{\r\n"
						+ "    \"fields\": {\r\n"
						+ "        \"project\": {\r\n"
						+ "            \"key\": \"SCRUM\"\r\n"
						+ "        },\r\n"
						+ "        \"summary\": \"Checkbox items are not working- automation Rest Assured\",\r\n"
						+ "        \"issuetype\": {\r\n"
						+ "            \"name\": \"Bug\"\r\n"
						+ "        }\r\n"
						+ "    }\r\n"
						+ "}")		
				.log().all()		
			.post("rest/api/3/issue")
			.then()
				.log().all()
				.assertThat()
				.statusCode(201)
				.contentType("application/json")		
				.extract().response().asString();		 		 
		JsonPath js = new JsonPath(createIssueResponse);		 
		String issueId = js.getString("id");		 
		System.out.println(issueId);		 		 

		//Add Attachment
		System.out.println("Adding Attachment");

		String addAttachmentResponse= given()			
			.pathParam("id", issueId)			
			.header("X-Atlassian-Token","no-check")			
			.header("Authorization",AuthorisationToken)			
			.multiPart("file",new File("src\\main\\java\\JiraAPITest\\jira-bpm.png"))
			.multiPart("file",new File("src\\main\\java\\JiraAPITest\\VS-1.png"))
			.log().all()			
		.post("rest/api/3/issue/{id}/attachments")
		.then()
			.log().all()
			.assertThat()
			.statusCode(200)
			.extract().response().asString();
		
		
		
		JsonPath js2=new JsonPath(addAttachmentResponse);
		String filename=js2.getString("filename");
		System.out.println("Filenames fetched from isue are : "+filename);
		
		
		//Get Issue
		
		System.out.println("Get issue");
		String getIssueResponse= given().pathParam("id", issueId)
				.header("Content-Type","application/json")
				.header("Authorization",AuthorisationToken)	
			.get("/rest/api/2/issue/{id}")
			.then()
				.log().all()
				.assertThat()
				.statusCode(200)
				.extract().response().asString();
		JsonPath js3=new JsonPath(getIssueResponse);
		String fetchedSummary=js3.get("fields.summary");
		String fetchedIssueKey=js3.getString("key");
		System.out.println("Issue ID : "+issueId);
		System.out.println("Issue Key : "+fetchedIssueKey);
		System.out.println("Fetched summary : "+fetchedSummary);
		
		
		//Edit issue
		
		System.out.println("Edit issue");
		given().pathParam("id", issueId)
				.header("Content-Type","application/json")
				.header("Authorization",AuthorisationToken)	
				.body("{\r\n"
						+ "    \"fields\": {\r\n"
						+ "        \"project\": {\r\n"
						+ "            \"key\": \"SCRUM\"\r\n"
						+ "        },\r\n"
						+ "        \"summary\": \"Checkbox selections are not working- automation Rest Assured Updated\",\r\n"
						+ "        \"issuetype\": {\r\n"
						+ "            \"name\": \"Bug\"\r\n"
						+ "        }\r\n"
						+ "    }\r\n"
						+ "}")
				.log().all()
			.put("/rest/api/2/issue/{id}")
			.then()
				.log().all()
				.assertThat()
				.statusCode(204);
		
		
		//Get Issue
		
				System.out.println("Get issue");
				String getIssueResponse2= given().pathParam("id", issueId)
						.header("Content-Type","application/json")
						.header("Authorization",AuthorisationToken)	
					.get("/rest/api/2/issue/{id}")
					.then()
						.log().all()
						.assertThat()
						.statusCode(200)
						.extract().response().asString();
				JsonPath js4=new JsonPath(getIssueResponse2);
				String fetchedSummary2=js4.get("fields.summary");
				String fetchedIssueKey2=js4.getString("key");
				System.out.println("Issue ID : "+issueId);
				System.out.println("Issue Key : "+fetchedIssueKey2);
				System.out.println("Fetched summary : "+fetchedSummary2);
				
				
		//Delete Issue
				
		System.out.println("Delete Issue");
		
		given().pathParam("id", issueId)
			.header("Authorization",AuthorisationToken)	
		.delete("/rest/api/2/issue/{id}")
		.then()
			.log().all()
			.assertThat()
			.statusCode(204);
		
		//Get Issue to check whether deleted
		
		System.out.println("Get issue to check whether deleted");
		String getResponseStringAdterDel= given().pathParam("id", issueId)
				.header("Content-Type","application/json")
				.header("Authorization",AuthorisationToken)	
			.get("/rest/api/2/issue/{id}")
			.then()
				.log().all()
				.assertThat()
				.statusCode(404)
				
				.body("errorMessages",contains("Issue does not exist or you do not have permission to see it."))
				.extract().response().asString();
		
		JsonPath js5=new JsonPath(getResponseStringAdterDel);
		String notFoundMessage=js5.getString("errorMessages[0]");
		
		System.out.println("Not Found Message : "+notFoundMessage);
		
	}

}
