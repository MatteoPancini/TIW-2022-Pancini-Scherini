package it.polimi.tiw.beans;

public class Comment {
	
	private int commentID;
	private int imageID;
	private int albumID;
	private int userID;
	private String text;
	

	public Comment(int commentID, int imageID, int albumID, int userID, String text) {
		this.commentID = commentID;
		this.imageID = imageID;
		this.albumID = albumID;
		this.userID = userID;
		this.text = text;
	}


	public int getCommentID() {
		return commentID;
	}


	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}


	public int getImageID() {
		return imageID;
	}


	public void setImageID(int imageID) {
		this.imageID = imageID;
	}


	public int getAlbumID() {
		return albumID;
	}


	public void setAlbumID(int albumID) {
		this.albumID = albumID;
	}


	public int getUserID() {
		return userID;
	}


	public void setUserID(int userID) {
		this.userID = userID;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}
	

}
