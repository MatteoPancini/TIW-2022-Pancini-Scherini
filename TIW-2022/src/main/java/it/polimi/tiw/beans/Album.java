package it.polimi.tiw.beans;

import java.util.Date;

public class Album {
	
	private int idAlbum;
	private int idUser;
	private String title;
	private Date creationDate;
	private String userAlbum;
	
	
	
	public Album() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Album(int idAlbum, int idUser, String title, Date creationDate) {
		super();
		this.idAlbum = idAlbum;
		this.idUser = idUser;
		this.title = title;
		this.creationDate = creationDate;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getUserAlbum() {
		return userAlbum;
	}

	public void setUserAlbum(String userAlbum) {
		this.userAlbum = userAlbum;
	}

	
	
}
