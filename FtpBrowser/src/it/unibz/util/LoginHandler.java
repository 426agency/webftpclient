package it.unibz.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandler extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		
        String email = req.getParameter("email");
        String password =req.getParameter("password");

        // do stuff here to log the user in ... 

        boolean myBool = false;
        if (myBool)
        {
            // the user is logged in, redirect them to another page
        }
        else
        {
            // the login failed, return them to login page
        	String destination  ="index.jsp#page2?msg=failed";        
        	resp.sendRedirect(resp.encodeRedirectURL(destination));
        }
		
	}
	
}
