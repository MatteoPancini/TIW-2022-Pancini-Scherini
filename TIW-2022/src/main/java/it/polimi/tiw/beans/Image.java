package it.polimi.tiw.beans;

import java.util.Date;

public class Image {
	private int idImage;
	private int idUser;
	private int idAlbum;
	private String description;
	private Date date;
	private String path;
	
	
	public Image(int idImage, int idUser, int idAlbum, String description, Date date, String path) {
		this.idImage = idImage;
		this.idUser = idUser;
		this.idAlbum = idAlbum;
		this.description = description;
		this.date = date;
		this.path = path;
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


	public int getIdAlbum() {
		return idAlbum;
	}


	public void setIdAlbum(int idAlbum) {
		this.idAlbum = idAlbum;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}
	
	
	

}
