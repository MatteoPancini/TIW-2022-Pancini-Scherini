package it.polimi.tiw.dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.beans.Image;

public class ImageDAO {
	private Connection connection;	
	
	public ImageDAO(Connection connection) {
        this.connection = connection;
    }
	
	public List<Image> findAllAlbumImages(int idAlbum) throws SQLException {
		List<Image> albumImagesList = new ArrayList<>();
		
        String imagesQuery = "SELECT * FROM image WHERE idAlbum = ? ORDER BY date DESC";
        
        ResultSet resultSet = null;
        PreparedStatement pstatement = null;
        
        try {
        	pstatement = connection.prepareStatement(imagesQuery);
        	pstatement.setInt(1, idAlbum);
        	resultSet = pstatement.executeQuery();
        	
        	while(resultSet.next()) {
        		Image albumImage = new Image();
        		albumImage.setIdImage(resultSet.getInt("idImage"));
        		albumImage.setIdUser(resultSet.getInt("idUser"));
        		albumImage.setIdAlbum(resultSet.getInt("idAlbum"));
        		albumImage.setTitle(resultSet.getString("title"));
        		albumImage.setDescription(resultSet.getString("description"));
        		albumImage.setDate(new Date(resultSet.getDate("date").getTime()));
        		albumImage.setPath(resultSet.getString("path"));
        		
        		albumImagesList.add(albumImage);
        				
        	}
        } catch(SQLException e) {
        	e.printStackTrace();
            throw new SQLException(e);
        } finally {
              try {
                resultSet.close();
              } catch (Exception e1) {
                throw new SQLException(e1);
              }
              try {
                pstatement.close();
              } catch (Exception e2) {
                throw new SQLException(e2);
              }
          }
        return albumImagesList;
	}
	
	public Image getImageFromId(int imageId) throws SQLException{
		Image selectedImage = new Image();
		
		String query = "SELECT * FROM image WHERE idImage = ?";
		
		ResultSet resultSet = null;
        PreparedStatement pstatement = null;
        
        try {
        	pstatement = connection.prepareStatement(query);
        	pstatement.setInt(1, imageId);
        	resultSet = pstatement.executeQuery();
        	resultSet.next();
        	selectedImage.setIdImage(resultSet.getInt("idImage"));
        	selectedImage.setIdUser(resultSet.getInt("idUser"));
        	selectedImage.setIdAlbum(resultSet.getInt("idAlbum"));
        	selectedImage.setTitle(resultSet.getString("title"));
        	selectedImage.setDescription(resultSet.getString("description"));
        	selectedImage.setDate(new Date(resultSet.getDate("date").getTime()));
        	selectedImage.setPath(resultSet.getString("path"));
        }catch(SQLException e) {
        	e.printStackTrace();

        } finally {
              try {
                resultSet.close();
              } catch (Exception e1) {
                throw new SQLException(e1);
              }
              try {
                pstatement.close();
              } catch (Exception e2) {
                throw new SQLException(e2);
              }
        }
        return selectedImage;
	}
	
	
	public void createNewImage(int idUser, int albumId, String imageTitle, String description, String imagePath) throws SQLException {
        String query = "INSERT INTO image (idUser, idAlbum, title, description, date, path) VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, albumId);
            preparedStatement.setString(3, imageTitle);
            preparedStatement.setString(4, description);
            preparedStatement.setDate(5, new java.sql.Date(new Date().getTime()));
            preparedStatement.setString(6, imagePath);
            preparedStatement.executeUpdate();
        }
    }

}
