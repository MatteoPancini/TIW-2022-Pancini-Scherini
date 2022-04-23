package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polimi.tiw.beans.User;

public class UserDAO {
	private Connection connection;

	public UserDAO(Connection connection) {
		this.connection = connection;
	}
	
	public User checkLoginCredentials(String username, String psw) throws SQLException {
		
		String userCheckQuery = "SELECT  idUser, username, email FROM user  WHERE username = ? AND password =?";
		
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
					user.setUserID(result.getInt("idUser"));
					user.setUsername(result.getString("username"));
					user.setEmail(result.getString("email"));
					return user;
				}
			}
		}catch(SQLException e) {e.printStackTrace();}
		return null;
		
	}
	
}