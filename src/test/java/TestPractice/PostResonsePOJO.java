package TestPractice;

public class PostResonsePOJO {
	private String name;
	private String name1;
	private String id;
	private String createdAt;
	
	
	public PostResonsePOJO() {
		super();
	}
	public PostResonsePOJO(String name, String name1, String id, String createdAt) {
		super();
		this.name = name;
		this.name1 = name1;
		this.id = id;
		this.createdAt = createdAt;
	}
	
	@Override
	public String toString() {
		return "PostResonsePOJO [name=" + name + ", name1=" + name1 + ", id=" + id + ", createdAt=" + createdAt + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
