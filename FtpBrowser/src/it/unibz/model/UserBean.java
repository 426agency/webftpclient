package it.unibz.model;

/**
 * This bean is used to save the Data needed in the whole application; This data is saved in the form of variables,
 * and each variable has a getter and a setter method 
 * 
 * Variables needed in the application:
 *   First, the Servlet needs to send the 'username' and 'password' of the user to the DAO, to check the user's validity
 *   	So we need two String variables for the username and password (String username, String password)
 *   
 *   Then , the DAO needs to tell the Servlet if the user is valid or not
 *   	So we need a boolean variable that represent that user's validity (boolean isValid)
 *   
 *   Finally, if the user is registered, the DAO needs to tell the JSP about his first and last names
 *   	So we need two String variables for the first and last names (String firstName, String lastName)
 * 
 * */

public class UserBean {
	
		private String username;
		private String password;
		public boolean valid;
	
	

	
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

