package it.polimi.tiw.projects.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import it.polimi.tiw.projects.beans.User;

public class UserDAO {
	
	private Connection connection;
	private String username;
	
	public UserDAO(Connection connection) {
		this.connection = connection;
	}
	
	public UserDAO(Connection connection, String username) {
		this.connection = connection;
		this.username = username;
	}
	
	
	public void createUser (String username, String password, String address) throws SQLException{
		String query = "INSERT into user (username, password, address) VALUES(?, ?, ?)";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setString(1, username);
			pstatement.setString(2, password);
			pstatement.setString(3, address);
			pstatement.executeUpdate();  // what does it returns??
		}
	}

	public User checkCredentials(String username, String password) throws SQLException {
		String query = "SELECT username, address FROM user WHERE username = ? AND password =?";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setString(1, username);
			pstatement.setString(2, password);
			try (ResultSet result = pstatement.executeQuery();) {
				if (!result.isBeforeFirst()) // no results, credential check failed
					return null;
				else {
					result.next();
					User user = new User();
					user.setUsername(result.getString("username"));
					user.setAddress(result.getString("address"));
					return user;
				}
			}catch (Exception E) {
				throw new SQLException();
			}
		}catch (Exception e) {
			throw new SQLException();
		}
	}
	
	public List<User> findAllUsers() throws SQLException{
		List<User> users = new ArrayList<User>();
		String query = "SELECT username FROM user";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					User user = new User();
					user.setUsername(result.getString("username"));
					users.add(user);
				}
			}
		}
		return users;
	}
	
	@Override
	public String toString () {
		return this.username;
	}

}
