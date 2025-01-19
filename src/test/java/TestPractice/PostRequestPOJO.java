package TestPractice;

public class PostRequestPOJO {
	private String name;
	private String name1;
	public PostRequestPOJO() {
		super();
	}
	public PostRequestPOJO(String name,String name1) {
		super();
		this.name=name;
		this.name1=name1;
	}
	public String getName() {
		return name;
	}
	public String getName1() {
		return name1;
	}
	public void setName(String name) {
		this.name=name;
	}
	public void setName1(String name1) {
		this.name1=name1;
	}
}
