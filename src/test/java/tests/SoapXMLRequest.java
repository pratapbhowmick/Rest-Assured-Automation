package tests;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;


import static io.restassured.RestAssured.baseURI;
import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class SoapXMLRequest {
	@Test
	public void validateSoapXML() throws IOException {
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
			.body("//*:AddResult.text()",equalTo("11"));
	}
}
