package it.unibz.util;

import it.unibz.controller.FtpConnectionDAO;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
  	if(request.getParameter("userID")!=null){
      response.setContentType("text/html;charset=UTF-8");

      FtpConnectionDAO catalog = new FtpConnectionDAO();
      ArrayList catalogItems=null;
      try{
      catalogItems = catalog.getItems(Integer.parseInt(request.getParameter("userID")));
      }
      catch(Exception e){
      	catalogItems= new ArrayList();
      }
      String callback = request.getParameter("callback");
      request.setAttribute("ftpConnections", catalogItems);
      request.setAttribute("callback", callback);
      
      RequestDispatcher dispatcher = request.getRequestDispatcher("ftpConnectionsJSon.jsp");
      dispatcher.include(request, response);}
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
