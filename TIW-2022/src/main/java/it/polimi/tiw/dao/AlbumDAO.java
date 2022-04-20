package it.polimi.tiw.dao;

import it.polimi.tiw.beans.Album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AlbumDAO {
	
	private Connection connection;
	
	public AlbumDAO(Connection connection) {
		this.connection = connection;
	}
	
	public List<Album> findAllAlbums() throws SQLException {
		List<Album> userAlbumList = new ArrayList<Album>();
		
		String albumQuery = "SELECT * FROM album ORDER BY creationDate DESC";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(albumQuery)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					
					Album userAlbum = new Album();
					
					userAlbum.setIdAlbum(resultSet.getInt("idAlbum"));
					userAlbum.setIdUser(resultSet.getInt("idUser"));
					userAlbum.setTitle(resultSet.getString("title"));
					userAlbum.setCreationDate(new Date(resultSet.getDate("creationDate").getTime()));
					
					userAlbumList.add(userAlbum);
					
				}
			}
		}
		
		return userAlbumList;
	}
	
	
	
	public void createNewAlbum(String albumTitle) throws SQLException {
		String newAlbumQuery = "INSERT INTO album (idUser, title, creationDate) VALUES (? ? ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(newAlbumQuery)) {
			
			//userId da prendere dalla session
			//problema sugli auto-increment
			preparedStatement.setString(2, albumTitle);
			preparedStatement.setDate(3, new java.sql.Date(new Date().getTime()));
			preparedStatement.executeUpdate();
			
		}
		
	}
}



