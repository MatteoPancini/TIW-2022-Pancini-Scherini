package it.polimi.tiw.beans;

public class Comment {
	

	private int idComment;
	private int idImage;
	private int idAlbum;
	private int idUser;
	private String text;
	
	
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public Comment(int idComment, int idImage, int idAlbum, int idUser, String text) {
		this.idComment = idComment;
		this.idImage = idImage;
		this.idAlbum = idAlbum;
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


	public int getIdAlbum() {
		return idAlbum;
	}


	public void setIdAlbum(int idAlbum) {
		this.idAlbum = idAlbum;
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