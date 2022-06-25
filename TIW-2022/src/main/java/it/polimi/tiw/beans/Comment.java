package it.polimi.tiw.beans;

public class Comment {
	

	private int idComment;
	private int idImage;
	private int idUser;
	private String text;
	private String username;
	
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public Comment(int idComment, int idImage, int idUser, String text) {
		this.idComment = idComment;
		this.idImage = idImage;
		this.idUser = idUser;
		this.text = text;
	}


	public int getIdComment() {
		return idComment;
	}


	public void setIdComment(int idComment) {
		this.idComment = idComment;
	}


	public int getIdImage() {
		return idImage;
	}


	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}


	public int getIdUser() {
		return idUser;
	}


	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}
	
}