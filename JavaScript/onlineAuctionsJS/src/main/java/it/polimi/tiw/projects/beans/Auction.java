package it.polimi.tiw.projects.beans;

import java.time.LocalDateTime;

public class Auction {
	
	private int id_code;
	private String username;
	private LocalDateTime expiry_date_time;
	private float minimum_offset;
	private float current_price;
	private boolean opened;
	private boolean closed;
	
	public Integer getId_Code() {
		return this.id_code;
	}
	public String getUsername() {
		return this.username;
	}
	
	public LocalDateTime getExpiry_Date_Time() {
		return this.expiry_date_time;
	}
	
	public float getMinimum_Offset() {
		return this.minimum_offset;
	}
	public float getCurrent_Price() {
		return this.current_price;
	}
	
	public boolean getOpened() {
		return this.opened;
	}
	
	public boolean getClosed() {
		return this.closed;
	}
	
	public void setId_Code(int id_code) {
		this.id_code = id_code;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setExpiry_Date_Time(LocalDateTime expiry_date_time) {
		this.expiry_date_time = expiry_date_time;
	}
	
	public void setMinimum_Offset(float minimum_offset) {
		this.minimum_offset = minimum_offset;
	}
	
	public void setCurrent_Price(float currrent_price) {
		this.current_price = currrent_price;
	}
	
	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
}
