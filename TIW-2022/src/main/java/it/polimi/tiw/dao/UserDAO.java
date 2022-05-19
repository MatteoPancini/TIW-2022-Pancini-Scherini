package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.beans.User;

public class UserDAO {
	private Connection connection;

	public UserDAO(Connection connection) {
		this.connection = connection;
	}
	
	public User checkLoginCredentials(String username, String psw) throws SQLException {
		
		String userCheckQuery = "SELECT idUser, username, email FROM user  WHERE username = ? AND password =?";
		
		try {
			PreparedStatement pstatement = connection.prepareStatement(userCheckQuery);
			pstatement.setString(1, username);
			pstatement.setString(2, psw);
			try (ResultSet result = pstatement.executeQuery();) {
				if (!result.isBeforeFirst()) // no results, credential check failed
					return null;
				else {
					result.next();
					User user = new User();
					user.setIdUser(result.getInt("idUser"));
					user.setUsername(result.getString("username"));
					user.setEmail(result.getString("email"));
					return user;
				}
			}
		}catch(SQLException e) {e.printStackTrace();}
		return null;
		
	}
	
	
	public List<String> findAllUsernames() throws SQLException{
		ArrayList<String> usernameList = new ArrayList<>();
		
		String query = "SELECT username FROM user";
		
		ResultSet resultSet = null;
	    
	    try(PreparedStatement pstatement = connection.prepareStatement(query);){
			
			resultSet = pstatement.executeQuery();
			
			while (resultSet.next())        
		          usernameList.add(resultSet.getString("username"));
		}
	    
		return usernameList;
	}
	
	public void createUser(String email, String username, String psw) throws SQLException {
		String query = "INSERT INTO dbfinalproject.user (email, username, password) VALUES(?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, psw);
			preparedStatement.executeUpdate();
		}
	}
	
	
	public int getIdFromUsername(String username) throws SQLException {
		
		String query = "SELECT idUser FROM user WHERE username = ?";
		
	    
		try {
			PreparedStatement pstatement = connection.prepareStatement(query);
			pstatement.setString(1, username);
			try (ResultSet result = pstatement.executeQuery();) {
				if (!result.isBeforeFirst()) // no results, credential check failed
					return -1;
				else {
					result.next();
					int userId = result.getInt("idUser");
					return userId;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
			}
		return -1;
		
		
		
	}
	
}