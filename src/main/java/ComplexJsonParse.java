import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	public static void main(String[] args) {
		JsonPath js= new JsonPath(Payload.coursePrice());
		//print count of course
		int countOfCourses=js.getInt("courses.size()");
		System.out.println("No of courses : "+countOfCourses);
		//print purchase amount
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase amount : "+purchaseAmount);
		//Print first course title
		System.out.println("First course : "+js.getString("courses[0].title"));
		
		int sumOfCopies=0;
		int sumOfPrice=0;
		int noOfCopiesSoldByRPA=0;
		for (int i = 0; i < countOfCourses; i++) {
			String courseTitle=js.getString("courses["+i+"].title");
			System.out.println("Course : "+courseTitle);
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			System.out.println("Price : "+price);
			System.out.println("------------------");
			if (courseTitle.equals("RPA")) {
				noOfCopiesSoldByRPA=copies;
			}
			sumOfPrice=sumOfPrice+price*copies;
			sumOfCopies=sumOfCopies+copies;
		}
		System.out.println("No of copies sold by RPA : "+noOfCopiesSoldByRPA);
		
		System.out.println("Sum of copies : "+sumOfCopies);
		System.out.println("Sum of prices : "+sumOfPrice);
		Assert.assertEquals(sumOfPrice,purchaseAmount );
	}
	
	
}
