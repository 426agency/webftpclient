package it.unibz.controller;

import it.unibz.dao.UserDAO;
import it.unibz.model.UserBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class responsible to delegate requests for Data retrieval and fetching
 * information using DAO and Managers for the Login of System users
 * 
 */
public class LoginServlet extends HttpServlet
{

	private static final long serialVersionUID = -7446330471621558077L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		try {

			if (request.getParameter("logout") != null) {

				HttpSession session = request.getSession();
				if (session != null)
					session.removeAttribute("currentSessionUser");
			} else {
				UserBean user = new UserBean(); // Create an instant of the bean
				// 'UserBean'

				user.setPassword(request.getParameter("password"));

				if (request.getParameter("username") != null) {
					// We are registering new user
					user.setUserName(request.getParameter("username")); // Get the
					// username and
					// password

					user = UserDAO.register(user);
				} else {

					user.setUserName(request.getParameter("login")); // Get the username
					// and password

					user = UserDAO.login(user); // Call the DAO to ask the DB about the
					// validity of this user
					// And send to it the user bean instance
					// (which currently contains the username and password values only)
				}

				if (user.isValid()) {

					HttpSession session = request.getSession(true); // Create a new
					// session instance
					// that will be with
					// us
					// throughout all the pages

					session.setAttribute("currentSessionUser", user);
					response.getOutputStream().println(user.getID());

				}

				else {
					response.getOutputStream().println("fail");

				}
			}
		}

		catch (Throwable theException)

		{
			System.out.println(theException);
		}

	}

}
