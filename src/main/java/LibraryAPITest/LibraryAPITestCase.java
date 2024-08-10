package LibraryAPITest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.nio.file.Files;

public class LibraryAPITestCase {
	@DataProvider(name = "BooksData")
	public Object[][] getData() {

		return new Object[][] {{"edffrv","22323"},{"efsfr","23241"},{"effrv","22322"}};
	}
	@Test(dataProvider = "BooksData")
	public void addBook(String sIsbn,String sAisle) {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		String response=given()
			.header("Content-Type","application/json")
			//.body(new File("src\\main\\java\\LibraryAPITest\\AddBook.json"))
			.body(LibraryAPIPayload.addBook(sIsbn,sAisle))
		.when()
			.post("Library/Addbook.php")
		.then()
			.log().all()
			.assertThat()
			.statusCode(200)
			.extract().response().asString();
		
		JsonPath js=new JsonPath(response);
		String id=js.getString("ID");
		System.out.println("New Book Id : "+id);
	}
}
