package it.unibz.util;

import it.unibz.controller.FavouritesDAO;
import it.unibz.controller.FtpConnectionDAO;
import it.unibz.model.FtpConnectionBean;
import it.unibz.model.UserBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

public class FtpConnectionsServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8868246447873132050L;

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
 // controlliamo se la request che è stata
 // effettuata contiene o meno un file
  	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
  	if (isMultipart) {
  		ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
  	  String ret="";

  		try {
  	  List fileItemsList = servletFileUpload.parseRequest(request);
  	  String optionalFileName = "";
  	  FileItem fileItem = null;

  	  Iterator it = fileItemsList.iterator();
  	  while (it.hasNext()){
  	    FileItem fileItemTemp = (FileItem)it.next();
  	    if (fileItemTemp.isFormField()){
  	      if (fileItemTemp.getFieldName().equals("filename"))
  	        optionalFileName = fileItemTemp.getString();
  	    }
  	    else
  	      fileItem = fileItemTemp;
  	  }

  	  if (fileItem!=null){
  	    String fileName = fileItem.getName();

  	    /* Save the uploaded file if its size is greater than 0. */
  	    if (fileItem.getSize() > 0){
  	      if (optionalFileName.trim().equals(""))
  	        fileName = FilenameUtils.getName(fileName);
  	      else
  	        fileName = optionalFileName;

  	      //String path=getServletContext().getRealPath("temp")+(getServletContext().getRealPath("temp").contains("/")?"/":"\\");
  	      //File saveTo = new File(path + fileName);
  	      
  	       //fileItem.write(saveTo);
  	        
    				FTPConnectionManager ftpconmgr=(FTPConnectionManager)request.getSession().getAttribute("connectionmanager");
    				
    				boolean result =ftpconmgr.uploadFile(s.getAttribute("currentfolder").toString(),fileName,fileItem.getInputStream());
  	      	if(result){
    				ret+= "<div id=\"status\">success</div>";
  	  			ret+="<div id=\"message\">Successfully Uploaded</div>";
  	  			//return the upload file
  	  			ret+="<div id=\"uploadedfile\">"+fileName+"</div>";}
  	      	else
  	      	{
  	        	ret+="<div id=\"status\">failed</div>";
  	  				ret+="<div id=\"message\">There is already such a File</div>";
  	      	}
  	      
  		}}}
      catch (Exception e){
      	ret+="<div id=\"status\">failed</div>";
				ret+="<div id=\"message\">"+e.getMessage()+"</div>";

		}
  		finally{
    		response.getOutputStream().println(ret);
  		}
 }else{
  	
  	
  	
  	UserBean user = null;
  	if(s!=null && s.getAttribute("currentSessionUser")!=null)
  		user= (UserBean)s.getAttribute("currentSessionUser");
  	else
return;
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
  			if((activity.equals("create")||activity.equals("edit"))&&user!=null){
  				FtpConnectionBean cb = new FtpConnectionBean();
  				cb.setHost(request.getParameter("host"));
  				cb.setPassword(request.getParameter("password"));
  				cb.setConnectionname(request.getParameter("connectionname"));
  				cb.setPort(request.getParameter("port").length()>0?Integer.parseInt(request.getParameter("port")):21);
  				cb.setUsername(request.getParameter("username"));
  				cb.setUserID(user.getID());
  				FtpConnectionDAO dao =  new FtpConnectionDAO();
  				response.getOutputStream().println((activity.equals("create")?dao.createConnection(cb):dao.editConnection(cb))?"success":"fail");

  			}
  			if(activity.equals("removeConnection")){
  				FtpConnectionDAO dao =  new FtpConnectionDAO();
  				FtpConnectionBean cb = new FtpConnectionBean();
  				cb.setConnectionname(request.getParameter("connectionname"));
  				cb.setUserID(user.getID());
  				response.getOutputStream().println(dao.removeConnection(cb)?"success":"fail");
  				
  			}
  			if(activity.equals("getfolders")){
  				FtpConnectionDAO dao =  new FtpConnectionDAO();
  				String connectionname=request.getParameter("connectionname");
  				HttpSession ss = request.getSession();
  				FTPConnectionManager ftpconmgr=null;
  				//First check if no connectionname
  				if(ss.getAttribute("connectionmanager")==null){
  					//ss.setAttribute("connectionname", connectionname);
    				FtpConnectionBean cb=dao.getItem(user.getID(),connectionname);
  					ftpconmgr= new FTPConnectionManager(connectionname);
    				ftpconmgr.doConnection(cb.getUsername(),cb.getPassword(),cb.getHost(),cb.getPort());
    				ss.setAttribute("connectionmanager", ftpconmgr);
    				ss.setAttribute("currentfolder", request.getParameter("currentfolder"));
  				}
  				else{
  					ftpconmgr=(FTPConnectionManager)ss.getAttribute("connectionmanager");
  				}
  				//now check if connection still the same
  				if(ftpconmgr.getConnectionname().equals(connectionname)||connectionname.equals("/")){
  					ss.setAttribute("currentfolder", request.getParameter("currentfolder"));
  				}
  				else{
  					//Disconnect and create new  					
  					ftpconmgr.removeConnection();
  					FtpConnectionBean cb=dao.getItem(user.getID(),connectionname);
  					//ftpconmgr= new FTPConnectionManager(connectionname);
  					ftpconmgr.setConnectionname(connectionname);
    				ftpconmgr.doConnection(cb.getUsername(),cb.getPassword(),cb.getHost(),cb.getPort());
    				ss.setAttribute("connectionmanager", ftpconmgr);
    				ss.setAttribute("currentfolder", request.getParameter("currentfolder"));
  				}
  				
  				
  	  	      ArrayList catalogItems=null;
  	  	    	if(user!=null){    	    
  	  	    		catalogItems =  ftpconmgr.getFileList(request.getParameter("currentfolder"));
  	  	    	}else
  	  	      	catalogItems= new ArrayList();
  				//ftpconmgr.removeConnection();

  				
  				 String callback = request.getParameter("callback");
  		  	      request.setAttribute("foldercontent", catalogItems);
  		  	      request.setAttribute("callback", callback);
  		  	      
  		  	      RequestDispatcher dispatcher = request.getRequestDispatcher("ftpFolderJSon.jsp");
  		  	      dispatcher.include(request, response);
  				
  			}
  			if(activity.equals("removeItem")){
  				FTPConnectionManager ftpconmgr=(FTPConnectionManager)request.getSession().getAttribute("connectionmanager");
  				response.getOutputStream().println(ftpconmgr.deleteItem((String)request.getParameter("itemname"),((String)request.getParameter("itemtype")).equals("1"))?"success":"fail");
  			}
  			if(activity.equals("makedir")){
  				FTPConnectionManager ftpconmgr=(FTPConnectionManager)request.getSession().getAttribute("connectionmanager");
  				response.getOutputStream().println(ftpconmgr.makeDirectory((String)request.getParameter("currentfolder")+"/"+((String)request.getParameter("dirname")))?"success":"fail");
  			}
  			if(activity.equals("renamename")){
  				FTPConnectionManager ftpconmgr=(FTPConnectionManager)request.getSession().getAttribute("connectionmanager");
  				response.getOutputStream().println(ftpconmgr.renameFileOrDir((String)request.getParameter("currentfolder"),
  						(String)request.getParameter("oldname"),(String)request.getParameter("renamename"))?"success":"fail");
  			}
  			if(activity.equals("downloadfile")){
  				FTPConnectionManager ftpconmgr=(FTPConnectionManager)request.getSession().getAttribute("connectionmanager");
  				
  				String filename=(String)request.getParameter("filename");
  			    response.getOutputStream().println(ftpconmgr.downloadFile((String)request.getParameter("currentfolder"),filename,getServletContext().getRealPath("temp")+(getServletContext().getRealPath("temp").contains("/")?"/":"\\"))?"success":"fail");
  	  			
  				
  			}
  			if(activity.equals("getallfavourites")){

  				response.setContentType("text/html;charset=UTF-8");

  	      FavouritesDAO catalog = new FavouritesDAO();
  	      ArrayList catalogItems=null;
  	    	if(user!=null)
  	    		catalogItems = catalog.getItems(user.getID());
  	    	else
  	      	catalogItems= new ArrayList();

  	      String callback = request.getParameter("callback");
  	      request.setAttribute("favourites", catalogItems);
  	      request.setAttribute("callback", callback);
  	      
  	      RequestDispatcher dispatcher = request.getRequestDispatcher("favouritesJSon.jsp");
  	      dispatcher.include(request, response);
  			}
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
