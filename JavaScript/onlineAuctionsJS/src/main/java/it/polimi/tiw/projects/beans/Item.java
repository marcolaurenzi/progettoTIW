package it.polimi.tiw.projects.beans;

public class Item {
	private int id_code;
	private String username;
	private String name;
	private String description;
	private String image;
	private float price;
	private boolean availability;
	
	public void setId_Code (int id_code) {
		this.id_code = id_code;
	}
	
	public void setUsername (String username) {
		this.username = username;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public void setDescription (String description) {
		this.description = description;
	}
	
	public void setImage (String image) {
		this.image = image;
	}
	
	public void setPrice (float price) {
		this.price = price;
	}
	
	public void setAvailability (boolean availability) {
		this.availability = availability;
	}
	
	public int getId_Code () {
		return this.id_code;
	}
	
	public String getUsername () {
		return this.username;
	}
	
	public String getName () {
		return this.name;
	}
	
	public String getDescription () {
		return this.description;
	}
	
	public String getImage () {
		return this.image;
	}
	
	public float getPrice () {
		return this.price;
	}
	
	public boolean getAvailability () {
		return this.availability;
	}
	
	public String toString() {
		Integer toRet = this.getId_Code();
		return toRet.toString();
	}
}
