package it.polimi.tiw.projects.beans;

import java.time.LocalDateTime;

public class Bid {
	private LocalDateTime date_time;
	private String username;
	private int auction_id;
	private float amount;
	
	public void setDate_Time (LocalDateTime date_time) {
		this.date_time = date_time;
	}

	public void setUsername (String username) {
		this.username = username;
	}

	public void setAuction__Id (int auction_id) {
		this.auction_id = auction_id;
	}

	public void setAmount (float amount) {
		this.amount = amount;
	}
	
	public LocalDateTime getDate_Time () {
		return date_time;
	}

	public String getUsername () {
		return username;
	}
	public int getAuction_Id () {
		return auction_id;
	}
	public float getAmount () {
		return amount;
	}
}
