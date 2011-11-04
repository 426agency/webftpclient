package it.unibz.controller;
import it.unibz.model.UserBean;
import it.unibz.util.ConnectionManager;

import java.sql.*;


/*Name: UserDAO.java 
 *Description:  stands for "User Data Access Object". This means it is responsible for accessing the 
 *				database for a certain type of bean (each bean will have a DAO class responsible for 
 *				all its operations)
 *				The UserDAO class includes all the behavior of the UserBean class.
 *
 *              This file contains only one example (login) of such methods. 
 *
 *              The UserDAO connects to the database using the ConnectionManager class then 
 *				, according to the info it gets from there, fills the Userbean and goes back
 *				to its invoking servlet (LoginServlet).
 *
 **/


	public class UserDAO 
	
	{
	static Connection currentCon = null;
	static ResultSet rs = null;  
	
	/*Method Name : logIn
	 *
	 *Parameter(s): UserBean >> the bean that the servlet sent which contains only the username 
	 *				and password of the current UserBean instance.
	 *
	 *Description: connects to DB, checks if the user exists , sets valid to true or false accordingly.
	 *
	 *return type: UserBean >> returns the bean instance to the caller servlet after the updates done to it.
	 * 
	 **/
	
	public static UserBean login(UserBean bean) {
	
		// Preparing some objects for connection 
		Statement stmt = null;    
		
		//  Get the username and password from the bean 
		// (Initially entered by the user and saved to the bean by the Servlet)
	    String username = bean.getUsername();   
	    String password = bean.getPassword();   
	    
	    
	    
	    // Build the query  SELECT * FROM users WHERE username='...' AND password= '...' 
	    String searchQuery =
	        "select * from users where username='"
	            + username
	            + "' AND password='"
	            + password
	            + "'";
	    
	    /*XStream x = new XStream();
	    
	    UserBean newB = (UserBean)x.fromXML(ConnectionManager.getWebServiceResponse(new String[]{"operation=getuser","query="+searchQuery}));
	    */
	    // "System.out.println" prints in the console; Normally used to trace the process
	    //System.out.println("Your user name is " + username); 
	    //System.out.println("Your password is " + password);
	    //System.out.println("Query: "+searchQuery);
	    
	    
	    try 
	    {
	    	
	    	//connect to DB 
	        currentCon = ConnectionManager.getConnection();
	        stmt=currentCon.createStatement();
	        
	        
	        rs = stmt.executeQuery(searchQuery); 	// Save the results of the query to the ResultSet 'rs'
	        boolean more = rs.next();            	// If 'rs' is empty, then more = false; else, more = true
	       

	        
	        // If the ResultSet is empty >>> user does not exist >>> set the isValid variable to false
	        if (!more) 
	        {
	            //System.out.println("Sorry, you are not a registered user! Please sign up first");

	            bean.setValid(false);
	        } 
	        
	        
	        // If the ResultSet is not empty >>> user exists >>> set the isValid variable to true 
	        // And save the user's First and Last names to the bean (To be displayed later by userLogged.jsp)
	        else if (more) 
	        {
	        	bean.setID(rs.getInt("id"));

	                bean.setValid(true);
	        }
	        
	        
	    } 
	    
	    catch (Exception ex) 
	    {
	    		//System.out.println("Log In failed: An Exception has occurred! " + ex);
	    } 
	    
	    
	    //Some exception handling
	    finally 
	    {
	        if (rs != null)	{
	            try {
	                rs.close();
	            } catch (Exception e) {}
	            rs = null;
	        }
	
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (Exception e) {}
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
	 * @param user User to insert
	 * @return Validated user object
	 */
	public static UserBean register(UserBean user) {
		String query = "INSERT INTO users (username,password) VALUES(?,?)";  
		try {  
		  PreparedStatement ps;  
		  ps = ConnectionManager.getConnection().prepareStatement(query);  
		  ps.setString(1, user.getUsername());  
		  ps.setString(2, user.getPassword());  
		  if(ps.executeUpdate() != 0){
		  	//Close PreparedStatement before fetching new data
		  	ps.close(); 
		  	return login(user);
		  }  
		  else{
			  ps.close();  

		  }
		  //TO get inserted ID
		} catch (Exception ex) {  
		} 
		return user;
	}	
}



