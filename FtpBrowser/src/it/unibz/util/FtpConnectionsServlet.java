package it.unibz.util;

import it.unibz.controller.FtpConnectionDAO;
import it.unibz.model.FtpConnectionBean;
import it.unibz.model.UserBean;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FtpConnectionsServlet extends HttpServlet
{
	/**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @SuppressWarnings("rawtypes")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
  	HttpSession s = 	request.getSession();
  	UserBean user = null;
  	if(s!=null && s.getAttribute("currentSessionUser")!=null)
  		user= (UserBean)s.getAttribute("currentSessionUser");
  	
  		if(request.getParameter("activity")!=null){
  			String activity=request.getParameter("activity");
  			if(activity.equals("getall")){
  				response.setContentType("text/html;charset=UTF-8");

  	      FtpConnectionDAO catalog = new FtpConnectionDAO();
  	      ArrayList catalogItems=null;
  	    	if(user!=null)
  	    		catalogItems = catalog.getItems(user.getID());
  	    	else
  	      	catalogItems= new ArrayList();

  	      String callback = request.getParameter("callback");
  	      request.setAttribute("ftpConnections", catalogItems);
  	      request.setAttribute("callback", callback);
  	      
  	      RequestDispatcher dispatcher = request.getRequestDispatcher("ftpConnectionsJSon.jsp");
  	      dispatcher.include(request, response);
  			}
  			if(activity.equals("create")&&user!=null){
  				FtpConnectionBean cb = new FtpConnectionBean();
  				cb.setHost(request.getParameter("host"));
  				cb.setPassword(request.getParameter("password"));
  				cb.setPort(request.getParameter("port").length()>0?Integer.parseInt(request.getParameter("port")):21);
  				cb.setUsername(request.getParameter("username"));
  				cb.setUserID(user.getID());
  				FtpConnectionDAO dao =  new FtpConnectionDAO();
  				if(dao.createConnection(cb))
  					response.getOutputStream().println("success");
  				else
  					response.getOutputStream().println("fail");
  			}
  			
  		}
  	
      
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
      processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
      processRequest(request, response);
  }



}
