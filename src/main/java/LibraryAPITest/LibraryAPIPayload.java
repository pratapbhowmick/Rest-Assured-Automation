package LibraryAPITest;

public class LibraryAPIPayload {

	public static String addBook(String sIsbn,String sAisle) {
		String payload="{\r\n"
				+ "\"name\":\"Learn Appium Automation with Java\",\r\n"
				+ "\"isbn\":\""+sIsbn+"\",\r\n"
				+ "\"aisle\":\""+sAisle+"\",\r\n"
				+ "\"author\":\"John foer\"\r\n"
				+ "}";
		return payload;
	}
}
