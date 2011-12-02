package it.unibz.model;

public class FavouriteBean {
	private int userID;
	private int connectionID;
	private String folderPATH;
	private String connectionNAME;
	public String getConnectionNAME() {
		return connectionNAME;
	}
	public void setConnectionNAME(String connectionNAME) {
		this.connectionNAME = connectionNAME;
	}
	public int getConnectionID() {
		return connectionID;
	}
	public void setConnectionID(int connectionID) {
		this.connectionID = connectionID;
	}
	public String getFolderPATH() {
		return folderPATH;
	}
	public void setFolderPATH(String folderPATH) {
		this.folderPATH = folderPATH;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
}
