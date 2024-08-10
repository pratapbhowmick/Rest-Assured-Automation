package tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class XMLSchemaValidation {
	@Test
	public void schemaValidation() throws IOException {
		FileInputStream fso=new FileInputStream(new File("./src/test/resources/add.xml"));
		if (new File("./src/main/resources/add.xml").exists()) {
			System.out.println("	>>	File Exists");
		}
		String requestBody=IOUtils.toString(fso,"UTF-8");
		baseURI="http://www.dneonline.com/";
		given()
			.contentType("text/xml")
			.accept(ContentType.XML)
			.body(requestBody)
		.when()
			.post("/calculator.asmx?op=Add")
		.then()
			.statusCode(200)
			.log().all()
		.and()
			.body("//*:AddResult.text()",equalTo("11"))
		.and()
			.assertThat().body(matchesXsdInClasspath("Calculator.xsd"));
	}
}
