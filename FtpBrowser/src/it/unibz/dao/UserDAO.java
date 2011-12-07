package it.unibz.dao;

import it.unibz.model.UserBean;
import it.unibz.util.SQLConnectionManager;
import java.sql.*;

/**
 * This class is responsible for accessing the database for the UserBean.
 * 
 * The UserDAO connects to the database using the ConnectionManager class
 * 
 */
public class UserDAO

{
	static Connection currentCon = null;

	static ResultSet rs = null;

	/**
	 * Method tries to log in user in the System.
	 * 
	 * @param bean
	 * @return a Validated "TRUE" BEan if theuser has successfully logged incmd
	 */
	public static UserBean login(UserBean bean) {

		// Preparing some objects for connection
		Statement stmt = null;

		// Get the username and password from the bean
		// (Initially entered by the user and saved to the bean by the Servlet)
		String username = bean.getUsername();
		String password = bean.getPassword();

		// Build the query SELECT * FROM users WHERE username='...' AND password=
		// '...'
		String searchQuery = "select * from users where username='" + username
				+ "' AND password='" + password + "'";

		try {

			// connect to DB
			currentCon = SQLConnectionManager.getConnection();
			stmt = currentCon.createStatement();

			rs = stmt.executeQuery(searchQuery); // Save the results of the query to
			// the ResultSet 'rs'
			boolean more = rs.next(); // If 'rs' is empty, then more = false; else,
			// more = true

			// If the ResultSet is empty >>> user does not exist >>> set the isValid
			// variable to false
			if (!more) {
				// System.out.println("Sorry, you are not a registered user! Please sign up first");

				bean.setValid(false);
			}

			// If the ResultSet is not empty >>> user exists >>> set the isValid
			// variable to true
			// And save the user's First and Last names to the bean (To be displayed
			// later by userLogged.jsp)
			else if (more) {
				bean.setID(rs.getInt("id"));

				bean.setValid(true);
			}

		}

		catch (Exception ex) {
			// System.out.println("Log In failed: An Exception has occurred! " + ex);
		}

		// Some exception handling
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
				stmt = null;
			}

			if (currentCon != null) {

				try {
					currentCon.close();
				} catch (Exception e) {
				}
				currentCon = null;

			}
		}
		return bean;

	}

	/**
	 * Method sends insert statement to remote postgres db
	 * 
	 * @param user
	 *          User to insert
	 * @return Validated user object
	 */
	public static UserBean register(UserBean user) {
		String query = "INSERT INTO users (username,password) VALUES(?,?)";
		try {
			PreparedStatement ps;
			ps = SQLConnectionManager.getConnection().prepareStatement(query);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			if (ps.executeUpdate() != 0) {
				// Close PreparedStatement before fetching new data
				ps.close();
				return login(user);
			} else {
				ps.close();

			}
			// TO get inserted ID
		} catch (Exception ex) {
		}
		return user;
	}
}
