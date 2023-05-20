package it.polimi.tiw.projects.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;

import java.util.*;

import it.polimi.tiw.projects.beans.Auction;
import it.polimi.tiw.projects.beans.Item;
import it.polimi.tiw.projects.beans.User;

public class AuctionDAO {

	private Connection connection;
		
	public AuctionDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void createAuction(int id_code, String username, LocalDateTime expiry_date_time, float minimum_offset) throws SQLException {
		
		String query1 = "INSERT into auction (id_code, username, expiry_date_time, minimum_offset, opened, closed) VALUES(?, ?, ?, ?, ?, ?)";
		PreparedStatement pstatement1 = null;
		
		try {
			pstatement1 = connection.prepareStatement(query1);
			Timestamp timestamp = Timestamp.valueOf(expiry_date_time);
			pstatement1.setInt(1, id_code);
			pstatement1.setString(2, username);
			pstatement1.setTimestamp(3, timestamp);
			pstatement1.setFloat(4, minimum_offset);
			pstatement1.setBoolean(5, false);
			pstatement1.setBoolean(6, false);
			pstatement1.executeUpdate();
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstatement1 != null) {
				try {
					pstatement1.close();
				} catch (Exception e) {
					throw e;
				}
			}
		}
	}
	
public void openAuction(int id_code) throws SQLException {
		String query1 = "UPDATE auction SET opened = 1 WHERE id_code = ?";
		PreparedStatement pstatement1 = null;
		
		try {
			pstatement1 = connection.prepareStatement(query1);
			pstatement1.setInt(1, id_code);
			pstatement1.executeUpdate();
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstatement1 != null) {
				try {
					pstatement1.close();
				} catch (Exception e) {
					throw e;
				}
			}
		}
	}
	
	public void addItem(int auctionId, int itemId) throws SQLException {
		
		connection.setAutoCommit(false);
		
		// dove faccio il controllo che le aste siano ancora pending per poter aggiungere?
		String query1 = "INSERT into contain (auction_id, item_id) VALUES (?, ?)";
		PreparedStatement pstatement1 = null;
		
		// vado a fare l'update solo sugli elementi che sono disponibili
		String query2 = "UPDATE item SET availability = false WHERE id_code = ? AND availability = true";
		PreparedStatement pstatement2 = null;
		
		try {
			pstatement1 = connection.prepareStatement(query1);
			pstatement1.setInt(1, auctionId);
			pstatement1.setInt(2, itemId);
			pstatement1.executeUpdate();
			
			pstatement2 = connection.prepareStatement(query2);
			pstatement2.setInt(1, itemId);
			pstatement2.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
			if (pstatement1 != null) {
				try {
					pstatement1.close();
				} catch (Exception e) {
					throw e;
				}
			}
			if (pstatement2 != null) {
				try {
					pstatement2.close();
				} catch (Exception e) {
					throw e;
				}
			}
		}
	}

	
	/**
	 * todo cambiare la ricerca usando anche il boolean closed
	 * @param keyWord the keyword that has to be contained in the name or the description of the item
	 * @return the list of open auctions that contains the keyword
	 * @throws SQLException
	 */
	public List<Auction> descendingOrderOpenAuctionsContainingKeyWord(String keyWord, User user) throws SQLException{
		Auction auction;
		List<Auction> l = new LinkedList<Auction>();
		Timestamp time;
		String query = "SELECT * "
	            + "FROM auction "
	            + "WHERE expiry_date_time > ? AND username <> ? AND opened = 1 AND id_code IN (SELECT auction_id "
	                                    					+ "FROM contain "
	                                    					+ "WHERE item_id IN (SELECT I.id_code "
	                                    										+ "FROM item AS I "
	                                    										+ "WHERE I.name LIKE CONCAT('%', ?, '%') OR I.description LIKE CONCAT('%', ?, '%'))) "
	            + "ORDER BY expiry_date_time DESC";

		
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			time = Timestamp.valueOf(LocalDateTime.now());
			pstatement.setTimestamp(1, time);
			pstatement.setString(2, user.getUsername());
			pstatement.setString(3, keyWord);
			pstatement.setString(4, keyWord);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					auction = new Auction();
					auction.setId_Code(result.getInt("id_code"));
					auction.setUsername(result.getString("username"));
					auction.setExpiry_Date_Time(result.getTimestamp("expiry_date_time").toLocalDateTime());
					auction.setMinimum_Offset(result.getInt("minimum_offset"));
					l.add(auction);
				}
			}
		}
		return l;
	}
	
	public List<Auction> descendingOrderOpenAuction (User user) throws SQLException{
		Auction auction;
		List<Auction> l = new LinkedList<Auction>();
		Timestamp time;
		//io avrei messo ASC
		String username = user.getUsername();
		
		String query = "SELECT * "
				+ "FROM auction "
				+ "WHERE expiry_date_time > ? AND opened = 1 AND username <> ? "
				+ "ORDER BY expiry_date_time DESC";
		
		
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			time = Timestamp.valueOf(LocalDateTime.now());
			pstatement.setTimestamp(1, time);
			pstatement.setString(2, username);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					auction = new Auction();
					auction.setId_Code(result.getInt("id_code"));
					auction.setUsername(result.getString("username"));
					auction.setExpiry_Date_Time(result.getTimestamp("expiry_date_time").toLocalDateTime());
					auction.setMinimum_Offset(result.getInt("minimum_offset"));
					l.add(auction);
				}
			}
		}
		return l;
	}
	
	public List<Auction> auctionWinningBy(String username) throws SQLException {
		List<Auction> l = new LinkedList<Auction>();
		Auction auction;
		Timestamp time;
		
		//select the auctions where the last offer was made by the username passed as a parameter.
		String query = "SELECT A.id_code, A.username, B.amount "
					+ "FROM bid B JOIN auction A ON B.auction_id = A.id_code "
					+ "WHERE B.username = ? AND A.expiry_date_time > ? AND A.expiry_date_time - B.date_time = (SELECT MIN(AUCTION.expiry_date_time - BID.date_time) "
																					+ "FROM bid AS BID JOIN auction AS AUCTION ON auction_id = id_code "
																					+ "WHERE AUCTION.id_code = B.auction_id)";

		try (PreparedStatement pstatement = connection.prepareStatement(query)){
			pstatement.setString(1, username);
			time = Timestamp.valueOf(LocalDateTime.now());
			pstatement.setTimestamp(2, time);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					auction = new Auction();
					auction.setId_Code(result.getInt("id_code"));
					auction.setUsername(result.getString("username"));
					auction.setCurrent_Price(result.getFloat("amount"));
					l.add(auction);
				}
			}
		}
		return l;

	}
	
	public List<Auction> auctionWonBy(String username) throws SQLException {
		List<Auction> l = new LinkedList<Auction>();
		Auction auction;
		Timestamp time;
		
		//select the auctions where the last offer was made by the username passed as a parameter.
		String query = "SELECT A.id_code, A.username, B.amount "
					+ "FROM bid B JOIN auction A ON auction_id = id_code "
					+ "WHERE B.username = ? AND A.expiry_date_time < ? AND A.expiry_date_time - B.date_time = (SELECT MIN(AUCTION.expiry_date_time - BID.date_time) "
																					+ "FROM bid AS BID JOIN auction AS AUCTION ON auction_id = id_code "
																					+ "WHERE AUCTION.id_code = B.auction_id)";

		try (PreparedStatement pstatement = connection.prepareStatement(query)){
			pstatement.setString(1, username);
			time = Timestamp.valueOf(LocalDateTime.now());
			pstatement.setTimestamp(2, time);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					auction = new Auction();
					auction.setId_Code(result.getInt("id_code"));
					auction.setUsername(result.getString("username"));
					auction.setCurrent_Price(result.getFloat("amount"));
					l.add(auction);
				}
			}
		}
		return l;

	}
	
	public List<Auction> findAllExpiredAuctions(User user) throws SQLException {
		Timestamp time = null;
		List<Auction> auctions = new ArrayList<Auction>();
		String query = "SELECT * FROM auction WHERE username = ? AND expiry_date_time < ? AND closed = 0";
		PreparedStatement pstatement = null;
		ResultSet result =  null;
		try {
			time = Timestamp.valueOf(LocalDateTime.now());
			pstatement = connection.prepareStatement(query);
			pstatement.setString(1, user.getUsername());
			pstatement.setTimestamp(2, time);
			result = pstatement.executeQuery();
			while (result.next()) {
				Auction auction = new Auction();
				auction.setUsername(result.getString("username"));
				auction.setId_Code(result.getInt("id_code"));
				LocalDateTime temp = result.getTimestamp("expiry_date_time").toLocalDateTime();
				auction.setExpiry_Date_Time(temp);
				auction.setMinimum_Offset(result.getFloat("minimum_offset"));
				auction.setOpened(result.getBoolean("opened"));
				auction.setClosed(result.getBoolean("closed"));
				auctions.add(auction);
			}
		} catch (SQLException e) {
		    e.printStackTrace();
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException(e1);
			}
			try {
				pstatement.close();
			} catch (Exception e2) {
				throw new SQLException(e2);
			}
		}		
		return auctions;
	}
	
	public List<Integer> findAllExpiredAuctionsId(User user) throws SQLException {
		Timestamp time = null;
		List<Integer> auctionsId = new ArrayList<Integer>();
		String query = "SELECT id_code FROM auction WHERE username = ? AND expiry_date_time < ? AND closed = 0";
		PreparedStatement pstatement = null;
		ResultSet result =  null;
		try {
			time = Timestamp.valueOf(LocalDateTime.now());
			pstatement = connection.prepareStatement(query);
			pstatement.setString(1, user.getUsername());
			pstatement.setTimestamp(2, time);
			result = pstatement.executeQuery();
			while (result.next()) {
				auctionsId.add(result.getInt("id_code"));
			}
		} catch (SQLException e) {
		    e.printStackTrace();
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException(e1);
			}
			try {
				pstatement.close();
			} catch (Exception e2) {
				throw new SQLException(e2);
			}
		}		
		return auctionsId;
	}
	
	public List<Auction> findAllToOpenAuctions(User user) throws SQLException {
		List<Auction> auctions = new ArrayList<Auction>();
		String query = "SELECT * FROM auction WHERE username = ? AND opened = 0 AND closed = 0";
		PreparedStatement pstatement = null;
		ResultSet result =  null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setString(1, user.getUsername());
			result = pstatement.executeQuery();
			while (result.next()) {
				Auction auction = new Auction();
				auction.setUsername(result.getString("username"));
				auction.setId_Code(result.getInt("id_code"));
				LocalDateTime temp = result.getTimestamp("expiry_date_time").toLocalDateTime();
				auction.setExpiry_Date_Time(temp);
				auction.setMinimum_Offset(result.getFloat("minimum_offset"));
				auction.setOpened(result.getBoolean("opened"));
				auction.setClosed(result.getBoolean("closed"));
				auctions.add(auction);
			}
		} catch (SQLException e) {
		    e.printStackTrace();
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException(e1);
			}
			try {
				pstatement.close();
			} catch (Exception e2) {
				throw new SQLException(e2);
			}
		}		
		return auctions;
	}
	
	public List<Integer> findAllToOpenAuctionId(User user) throws SQLException {
		List<Integer> auctions = new ArrayList<Integer>();
		String query = "SELECT id_code FROM auction WHERE username = ? AND expiry_date_time > ? AND opened = 0 AND closed = 0";
		PreparedStatement pstatement = null;
		ResultSet result =  null;
		Timestamp time = null;
		try {
			time = Timestamp.valueOf(LocalDateTime.now());
			pstatement = connection.prepareStatement(query);
			pstatement.setString(1, user.getUsername());
			pstatement.setTimestamp(2, time);
			result = pstatement.executeQuery();
			while (result.next()) {
				auctions.add(result.getInt("id_code"));
			}
		} catch (SQLException e) {
		    e.printStackTrace();
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException(e1);
			}
			try {
				pstatement.close();
			} catch (Exception e2) {
				throw new SQLException(e2);
			}
		}		
		return auctions;
	}
	
	public List<Auction> findAllClosedAuctions(User user) throws SQLException {
		List<Auction> auctions = new ArrayList<Auction>();
		String query = "SELECT * FROM auction WHERE username = ? AND closed = 1";
		PreparedStatement pstatement = null;
		ResultSet result =  null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setString(1, user.getUsername());
			result = pstatement.executeQuery();
			while (result.next()) {
				Auction auction = new Auction();
				auction.setUsername(result.getString("username"));
				auction.setId_Code(result.getInt("id_code"));
				LocalDateTime temp = result.getTimestamp("expiry_date_time").toLocalDateTime();
				auction.setExpiry_Date_Time(temp);
				auction.setMinimum_Offset(result.getFloat("minimum_offset"));
				auction.setOpened(result.getBoolean("opened"));
				auction.setClosed(result.getBoolean("closed"));
				auctions.add(auction);
			}
		} catch (SQLException e) {
		    e.printStackTrace();
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException(e1);
			}
			try {
				pstatement.close();
			} catch (Exception e2) {
				throw new SQLException(e2);
			}
		}		
		return auctions;
	}
	
	public List<Auction> findAllOpenedAuctions(User user) throws SQLException {
		Timestamp time = null;
		List<Auction> auctions = new ArrayList<Auction>();
		String query = "SELECT * FROM auction WHERE username = ? AND expiry_date_time > ? AND opened = 1 AND closed = 0";
		PreparedStatement pstatement = null;
		ResultSet result =  null;
		try {
			time = Timestamp.valueOf(LocalDateTime.now());
			pstatement = connection.prepareStatement(query);
			pstatement.setString(1, user.getUsername());
			pstatement.setTimestamp(2, time);
			result = pstatement.executeQuery();
			while (result.next()) {
				Auction auction = new Auction();
				auction.setUsername(result.getString("username"));
				auction.setId_Code(result.getInt("id_code"));
				LocalDateTime temp = result.getTimestamp("expiry_date_time").toLocalDateTime();
				auction.setExpiry_Date_Time(temp);
				auction.setMinimum_Offset(result.getFloat("minimum_offset"));
				auction.setOpened(result.getBoolean("opened"));
				auction.setClosed(result.getBoolean("closed"));
				auctions.add(auction);
			}
		} catch (SQLException e) {
		    e.printStackTrace();
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException(e1);
			}
			try {
				pstatement.close();
			} catch (Exception e2) {
				throw new SQLException(e2);
			}
		}		
		return auctions;
	}
	
	public List<Integer> findAllOpenedAuctionId(User user) throws SQLException {
		Timestamp time = null;
		List<Integer> auctions = new ArrayList<Integer>();
		String query = "SELECT id_code FROM auction WHERE username = ? AND expiry_date_time > ? AND opened = 1 AND closed = 0";
		PreparedStatement pstatement = null;
		ResultSet result =  null;
		try {
			time = Timestamp.valueOf(LocalDateTime.now());
			pstatement = connection.prepareStatement(query);
			pstatement.setString(1, user.getUsername());
			pstatement.setTimestamp(2, time);
			result = pstatement.executeQuery();
			while (result.next()) {
				auctions.add(result.getInt("id_code"));
			}
		} catch (SQLException e) {
		    e.printStackTrace();
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException(e1);
			}
			try {
				pstatement.close();
			} catch (Exception e2) {
				throw new SQLException(e2);
			}
		}		
		return auctions;
	}
	

	public int getNextIdCode() throws SQLException {
		String query = "SELECT MAX(id_code) FROM auction";
		int toRet = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				toRet = result.getInt(1);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return toRet;
	}
	
	public Auction getAuctionDetails(int id_code) {
		String query = "SELECT * FROM auction WHERE id_code = ?";
		Auction auction = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, id_code);
			ResultSet result = pstatement.executeQuery();
			if (result.next()) {
				auction = new Auction();
				auction.setUsername(result.getString("username"));
				auction.setId_Code(result.getInt("id_code"));
				LocalDateTime temp = result.getTimestamp("expiry_date_time").toLocalDateTime();
				auction.setExpiry_Date_Time(temp);
				auction.setMinimum_Offset(result.getFloat("minimum_offset"));
				
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return auction;
		
	}
	
	public Float getTotalValue(int id_code) throws SQLException {
		ItemDAO itemDao = new ItemDAO(connection);
		List<Item> items = new ArrayList<Item>();
		try {
			items = itemDao.getItems(id_code);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		float toRet = 0;
		for(Item i : items) {
			String query ="SELECT price FROM item WHERE id_code = ?";
			try (PreparedStatement pstatement = connection.prepareStatement(query)) {
				pstatement.setInt(1, i.getId_Code());
				try (ResultSet result = pstatement.executeQuery()) {
					if (result.next()) {
						toRet += result.getFloat(1);
					}
				}
			}
		}
		
		
		return toRet;
	}
	
	public void closeAuction(int id_code) {
	
		PreparedStatement pstatement = null;
		String query = "UPDATE auction SET closed = 1 WHERE id_code = ?";
		
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, id_code);
			pstatement.executeUpdate();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public boolean containsInOpenAuctions (int auctionId) throws SQLException {
		Timestamp time = null;
		String query = "SELECT * FROM auction WHERE id_code = ? AND expiry_date_time >= ? AND opened = 1";
		PreparedStatement pstatement = null;
		ResultSet result =  null;
		try {
			time = Timestamp.valueOf(LocalDateTime.now());
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, auctionId);
			pstatement.setTimestamp(2, time);
			result = pstatement.executeQuery();
			return result.isBeforeFirst();
		} catch (SQLException e) {
		    e.printStackTrace();
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException(e1);
			}
			try {
				pstatement.close();
			} catch (Exception e2) {
				throw new SQLException(e2);
			}
		}
	}
	
}
