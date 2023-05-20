package it.polimi.tiw.projects.DAO;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Base64;

import it.polimi.tiw.projects.beans.Item;
import it.polimi.tiw.projects.beans.User;


public class ItemDAO {

	private Connection connection;
	
	public ItemDAO (Connection connection) {
		this.connection = connection;
	}
	
	public void createItem(int id_code, String username, String name, String description, InputStream imageStream, float price, boolean availability) throws SQLException{
		String query = "INSERT into item (id_code, username, name, description, image, price, availability) VALUES(?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, id_code);
			pstatement.setString(2, username);
			pstatement.setString(3, name);
			pstatement.setString(4, description);
			pstatement.setBlob(5, imageStream);
			pstatement.setFloat(6, price);
			pstatement.setBoolean(7, availability);
			pstatement.executeUpdate();  // what does it returns??
		}
	}
	
	public int getNextIdCode() throws SQLException {
		String query = "SELECT MAX(id_code) FROM item";
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
	
	public List<Item> findAllAvailableItemsAuctions(User user) throws SQLException {
		List<Item> items = new ArrayList<Item>();
		String query = "SELECT * FROM item WHERE username = ? AND availability = true";
		PreparedStatement pstatement = null;
		ResultSet result =  null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setString(1, user.getUsername());
			result = pstatement.executeQuery();
			while (result.next()) {
				Item item = new Item();
				item.setId_Code(result.getInt("id_code"));
				item.setName(result.getString("name"));
				items.add(item);
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
		return items;
	}
	
	public List<Integer> findAllAvailableItemsIdCode(User user) throws SQLException {
		List<Integer> items = new ArrayList<Integer>();
		String query = "SELECT * FROM item WHERE username = ? AND availability = true";
		PreparedStatement pstatement = null;
		ResultSet result =  null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setString(1, user.getUsername());
			result = pstatement.executeQuery();
			while (result.next()) {
				items.add(result.getInt("id_code"));
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
		return items;
	}
	
	public List<Item> findItems(int auctionIdCode) throws SQLException {
		List<Item> items = new LinkedList<Item>();
		String query = "SELECT * "
				+ "FROM item JOIN contain ON item_id = id_code "
				+ "WHERE  auction_id = ? ";
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setInt(1, auctionIdCode);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Item item = new Item();
					byte[] imgData = result.getBytes("image");
					if (imgData != null) {
						String encodedImg=Base64.getEncoder().encodeToString(imgData);
						item.setImage(encodedImg);
					}
					else {
						item.setImage(null);
					}
					item.setUsername(result.getString("username"));
					item.setId_Code(result.getInt("id_code"));
					item.setDescription(result.getString("description"));
					item.setName(result.getString("name"));
					item.setPrice(result.getFloat(6));
					item.setAvailability(result.getBoolean(7));
					items.add(item);
				}
			}
		}
		return items;
	}
	
	public Item getItem(int id_code) throws SQLException {
		Item item = null;
		String query = "SELECT * FROM item WHERE id_code = ?"; 
		try (PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setInt(1, id_code);
			try (ResultSet result = pstatement.executeQuery()) {
				if (result.next()) {
					item = new Item();
					byte[] imgData = result.getBytes("image");
					String encodedImg=Base64.getEncoder().encodeToString(imgData);
					item.setImage(encodedImg);
					item.setUsername(result.getString("username"));
					item.setId_Code(result.getInt("id_code"));
					item.setDescription(result.getString("description"));
					item.setName(result.getString("name"));
					item.setPrice(result.getFloat(6));
					item.setAvailability(result.getBoolean(7));
				}
			}
		}
		return item;
	}
	
	public List<Item> getItems(int id_code) throws SQLException {
		Item item = null;
		List<Item> items = new ArrayList<Item>();
		String query1 = "SELECT * FROM contain WHERE auction_id = ?"; 
		try (PreparedStatement pstatement1 = connection.prepareStatement(query1)) {
			pstatement1.setInt(1, id_code);
			try (ResultSet result1 = pstatement1.executeQuery()) {
				while (result1.next()) {
					// per ogni elemento dell'asta cerco i dettagli di quell'elemento
					String query2 = "SELECT * FROM item WHERE id_code = ?"; 
					try (PreparedStatement pstatement2 = connection.prepareStatement(query2)){
						pstatement2.setInt(1, result1.getInt("item_id"));
						try (ResultSet result2 = pstatement2.executeQuery()){
							if (result2.next()) {
								item = new Item();
								byte[] imgData = result2.getBytes("image");
								if(imgData != null) {
									String encodedImg=Base64.getEncoder().encodeToString(imgData);
									item.setImage(encodedImg);
								}else {
									item.setImage(null);
								}
								item.setUsername(result2.getString("username"));
								item.setId_Code(result2.getInt("id_code"));
								item.setDescription(result2.getString("description"));
								item.setName(result2.getString("name"));
								item.setPrice(result2.getFloat(6));
								item.setAvailability(result2.getBoolean(7));
								items.add(item);
							}
						}
					}
					
				}
			}
		}
		return items;
	}
	
}
