package it.unibz.util;
import it.unibz.controller.UserDAO;
import it.unibz.model.UserBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The Servlet is mainly the logic of the application,
 * It processes the data entered by the user, and redirects him to the appropriate JSP 
 */

public class LoginServlet extends HttpServlet {


/**
	 * 
	 */
	private static final long serialVersionUID = -7446330471621558077L;

public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
	//response.sendRedirect("index.jsp?msg=<font color=\"red\"><i>Sorry, wrong username/password combination.</i></font>");
	//return;
	try
	{	    
		
		if(request.getParameter("logout")!=null){
			
			HttpSession session = request.getSession();
			if(session!=null)
			session.removeAttribute("currentSessionUser");
		}else{
			UserBean user = new UserBean();					// Create an instant of the bean 'UserBean'
			
			user.setPassword(request.getParameter("password"));	// in the fields named "un" and "pw", And save them to the bean
			
			
			if(request.getParameter("username")!=null){
				//We are registering new user
				user.setUserName(request.getParameter("username"));	// Get the username and password entered by the user in LoginPage.JSP

				user=UserDAO.register(user);
			}
			else{


	user.setUserName(request.getParameter("login"));	// Get the username and password entered by the user in LoginPage.JSP
	
	user = UserDAO.login(user);						// Call the DAO to ask the DB about the validity of this user
													// And send to it the user bean instance 
    												// (which currently contains the username and password values only)
			}
	
	// In case the DAO says that the user is valid, Show the JSP that welcomes the user
	// else, Show the JSP that asks the user to sign up first
	if (user.isValid())
	{
		
		HttpSession session = request.getSession(true);	 // Create a new session instance that will be with us 
														 // throughout all the jsp pages
		
		session.setAttribute("currentSessionUser",user); // Save the bean in the session to the variable 
														 // "currentSessionUser", so that userLogged.jsp can use it  
														 // later to display the user's first and last names
														 // Please take a look at userLogged.jsp now
		response.getOutputStream().println(user.getID());

		/*String u = request.getRequestURL().toString();
		u=u.substring(0,u.indexOf("FtpBrowser")+10)+"/index.jsp";
		response.sendRedirect(u);// Call a Welcome page  */    		
	}
		        
	else {
response.getOutputStream().println("fail");
		/*HttpSession session = request.getSession(true);
		session.setAttribute("msg","<font color=\"red\"><i>Sorry, wrong username/password combination.</i></font>");
		String u = request.getRequestURL().toString();
		u=u.substring(0,u.indexOf("FtpBrowser")+10)+"/index.jsp";
		response.sendRedirect(u);// Call a Welcome page   */   		
		 // Call a Please-Sign-up-fist page
	}      
		} 
	}
			
			
	catch (Throwable theException) 
		    
	{
		System.out.println(theException); 
	}		
	
	}	

}
