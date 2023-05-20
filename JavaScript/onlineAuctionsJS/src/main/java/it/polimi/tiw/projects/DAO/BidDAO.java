package it.polimi.tiw.projects.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import it.polimi.tiw.projects.beans.Bid;

public class BidDAO {
	
	private Connection connection;
	
	public BidDAO(Connection connection) {
		this.connection = connection;
	}
	
	public Float getHighestBid(int id_code) throws SQLException {
		Float toRet = null;
		String query ="SELECT MAX(amount) FROM bid WHERE auction_id = ?";
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setInt(1, id_code);
			try (ResultSet result = pstatement.executeQuery()) {
				if (result.next()) {
					toRet = result.getFloat(1);
				}
			}
		}
		return toRet;
	}
	
	public List <Bid> getBidsFor (int auction_id) throws SQLException {
		List<Bid> l = new LinkedList<Bid>();
		Bid bid;
		
		String query = "SELECT * "
				+ "FROM bid "
				+ "WHERE auction_id = ? "
				+ "ORDER BY date_time DESC";
		
		
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setInt(1, auction_id);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					bid = new Bid();
					bid.setDate_Time(result.getTimestamp("date_time").toLocalDateTime());
					bid.setUsername(result.getString("username"));
					bid.setAmount(result.getFloat("amount"));
					l.add(bid);
				}
			}
		}
		return l;
	}
	
	public void createBid (String username, int auction_id, float amount) throws SQLException{
		Timestamp time = null;
		time = Timestamp.valueOf(LocalDateTime.now());
		String query = "INSERT into bid (date_time, username, auction_id, amount, sold) VALUES(?, ?, ?, ?, ?)";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setTimestamp(1, time);
			pstatement.setString(2, username);
			pstatement.setInt(3, auction_id);
			pstatement.setFloat(4, amount);
			pstatement.setBoolean(5, false);
			
			pstatement.executeUpdate();  // what does it returns??
		}
	}
}
