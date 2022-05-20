package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.beans.Comment;

public class CommentDAO {

	private final Connection connection;
    
    public CommentDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<Comment> findAllComments(int idImage, int idAlbum) throws SQLException {
    	
        List<Comment> imageComments = new ArrayList<>();
        
        
        
        String commentsQuery = "SELECT idImage,idAlbum,comment.idUser,username,text FROM comment JOIN user ON user.idUser=comment.idUser WHERE idImage = ?";
        
        ResultSet resultSet = null;
        PreparedStatement pstatement = null;
        
        
        try {
        	pstatement = connection.prepareStatement(commentsQuery);
        	pstatement.setInt(1, idImage);
        	resultSet = pstatement.executeQuery();
        	
        	while(resultSet.next()) {
        		Comment imageComment = new Comment();
        		
        		imageComment.setIdImage(resultSet.getInt("idImage"));
        		imageComment.setIdAlbum(resultSet.getInt("idAlbum"));
        		imageComment.setIdUser(resultSet.getInt("idUser"));
        		imageComment.setText(resultSet.getString("text"));
        		imageComment.setUsername(resultSet.getString("username"));
        		
        		imageComments.add(imageComment);
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
        return imageComments;
    }
    
    
    public void createNewComment(int idImage, int idAlbum, int idUser, String text) throws SQLException {
    	String query = "INSERT INTO comment(idImage, idAlbum, idUser, text) VALUES (?, ?, ?, ?)";
    	try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idImage);
            preparedStatement.setInt(2, idAlbum);
            preparedStatement.setInt(3, idUser);
            preparedStatement.setString(4, text);
            
            preparedStatement.executeUpdate();
        }

    }
}
