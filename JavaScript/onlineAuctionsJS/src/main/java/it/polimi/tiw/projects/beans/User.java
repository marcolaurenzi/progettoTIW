package it.polimi.tiw.projects.beans;

public class User {
	private String username;
	private String password;
	private String address;
	
	public void setUsername (String username) {
		this.username = username;
	}
	public String getUsername () {
		return this.username;
	}
	public void setPassword (String password) {
		this.password = password;
	}
	public String getPassword () {
		return this.password;
	}
	public void setAddress (String address) {
		this.address = address;
	}
	public String getAddress () {
		return this.address;
	}
}
