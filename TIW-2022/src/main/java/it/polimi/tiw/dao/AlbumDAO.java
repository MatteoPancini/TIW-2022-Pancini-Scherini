package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.polimi.tiw.beans.Album;


public class AlbumDAO {
  
  private Connection connection;
  
  public AlbumDAO(Connection connection) {
    this.connection = connection;
  }
  
  public List<Album> findUserAlbums(int idUser) throws SQLException {
    List<Album> userAlbumList = new ArrayList<Album>();
    
    
    String albumQuery = "SELECT * FROM album WHERE idUser = ? ORDER BY creationDate DESC";
    
    ResultSet resultSet = null;
    PreparedStatement pstatement = null;
    try {
      pstatement = connection.prepareStatement(albumQuery);
      pstatement.setInt(1, idUser);
      resultSet = pstatement.executeQuery();
      while (resultSet.next()) {          
          Album userAlbum = new Album();
                    
          userAlbum.setIdAlbum(resultSet.getInt("idAlbum"));
          userAlbum.setIdUser(resultSet.getInt("idUser"));
          userAlbum.setTitle(resultSet.getString("title"));
          userAlbum.setCreationDate(new Date(resultSet.getDate("creationDate").getTime()));
          
          userAlbumList.add(userAlbum);
          
        }
      } catch (SQLException e) {
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
        return userAlbumList;
      }
  
  
  
  public List<Album> findOtherAlbums(int idUser) throws SQLException {
    List<Album> userAlbumList = new ArrayList<Album>();
    
    
    String albumQuery = "SELECT * FROM album WHERE idUser NOT IN (SELECT idUser FROM album WHERE idUser = ?) ORDER BY creationDate DESC";
    
    ResultSet resultSet = null;
    PreparedStatement pstatement = null;
    try {
      pstatement = connection.prepareStatement(albumQuery);
      pstatement.setInt(1, idUser);
      resultSet = pstatement.executeQuery();
      while (resultSet.next()) {          
          Album userAlbum = new Album();
                    
          userAlbum.setIdAlbum(resultSet.getInt("idAlbum"));
          userAlbum.setIdUser(resultSet.getInt("idUser"));
          userAlbum.setTitle(resultSet.getString("title"));
          userAlbum.setCreationDate(new Date(resultSet.getDate("creationDate").getTime()));
          
          userAlbumList.add(userAlbum);
          
        }
      } catch (SQLException e) {
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
        return userAlbumList;
      }
  
  
  
  public void createNewAlbum(int idUser, String title) throws SQLException {
      String albumQuery = "INSERT INTO album (idUser, title, creationDate) VALUES (?, ?, ?)";
      try(PreparedStatement preparedStatement = connection.prepareStatement(albumQuery)) {
          preparedStatement.setInt(1, idUser);
          preparedStatement.setString(2, title);
          preparedStatement.setDate(3, new java.sql.Date(new Date().getTime()));
          preparedStatement.executeUpdate();
      }
  }
}