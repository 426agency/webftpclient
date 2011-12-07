package it.unibz.model;

/**
 * Class represents a single User object
 * 
 * Simple structure with private members and public access Methods
 * 
 */
public class UserBean
{

	private String username;

	private String password;

	public boolean valid;

	private int ID;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String newPassword) {
		password = newPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUserName(String newUsername) {
		username = newUsername;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean newValid) {
		valid = newValid;
	}
}
