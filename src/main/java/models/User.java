package models;

public class User {
	private int userId;
	private String name;
	private String email;
	private String password;
	private String role;
	
	public User(int userId, String name, String email, String role) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.role = role;
	}
	
	public User(String name, String email, String password, String role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail () {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}