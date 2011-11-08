package it.unibz.model;

/**
 * Class represents a single FTP Connection
 *
 */
public class FtpConnectionBean
{
	String username=null;
	String password=null;
	String host=null;
	String connectionname=null;
	public String getConnectionname() {
		return connectionname;
	}
	public void setConnectionname(String connectionname) {
		this.connectionname = connectionname;
	}
	int userID;
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	int port=21;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
}
