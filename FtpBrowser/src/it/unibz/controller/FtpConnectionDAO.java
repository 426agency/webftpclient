package it.unibz.controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import it.unibz.model.*;
import it.unibz.util.ConnectionManager;

public class FtpConnectionDAO
{
	private static String getItems = "SELECT * FROM ftpconnections where userid=";
	private static String getItem = "SELECT * FROM ftpconnections where connectionname=? AND userid=?";


  /**
   * Returns a list of all items
   */
  public ArrayList<FtpConnectionBean> getItems(int userid) {
      ArrayList<FtpConnectionBean> alCatalog = new ArrayList<FtpConnectionBean>();

      Statement stmt = null;
      ResultSet rs = null;
      Connection con = null;

      try {
          // establish a connection to the database via the driver
          con = ConnectionManager.getConnection();
          stmt = con.createStatement();
          rs = stmt.executeQuery(getItems+userid);
          while (rs.next()) {
          	FtpConnectionBean anItem = new FtpConnectionBean();
          	anItem.setUserID(userid);
          	anItem.setHost(rs.getString("host"));
              anItem.setPort(rs.getInt("port"));
              anItem.setUsername(rs.getString("username"));
              anItem.setPassword(rs.getString("password"));
              anItem.setConnectionname(rs.getString("connectionname"));
              alCatalog.add(anItem);
          }
      } catch (Exception e) {
          System.out.println("Exception executing query: "+e.getMessage());
      } finally {
          try {
              rs.close();
          } catch (Exception e) {
              System.out.println("Exception closing rs: "+e.getMessage());
          }
          try {
              stmt.close();
          } catch (Exception e) {
              System.out.println("Exception closing stmt: "+e.getMessage());
          }
          try {
              con.close();
          } catch (Exception e) {
              System.out.println("Exception closing con: "+e.getMessage());
          }
      }
      return alCatalog;
  }

	public boolean createConnection(FtpConnectionBean cb) {
		String query = "INSERT INTO ftpconnections (username,password,host,port,userid,connectionname) VALUES(?,?,?,?,?,?)";  
		try {  
		  PreparedStatement ps;  
		  ps = ConnectionManager.getConnection().prepareStatement(query);  
		  ps.setString(1, cb.getUsername());  
		  ps.setString(2, cb.getPassword());
		  ps.setString(3, cb.getHost());
		  ps.setInt(4, cb.getPort());
		  ps.setInt(5, cb.getUserID());
		  ps.setString(6, cb.getConnectionname());
		  if(ps.executeUpdate() != 0){
		  	//Close PreparedStatement before fetching new data
		  	ps.close(); 
		  	return true;
		  }  
		  else{
			  ps.close();  
			  return false;
		  }
		  //TO get inserted ID
		} catch (Exception ex) {  
			return false;
		} 
	}

	public boolean editConnection(FtpConnectionBean cb) {
		String query = "UPDATE ftpconnections SET username=?,password=?,host=?,port=? WHERE connectionname=?";  
		try {  
		  PreparedStatement ps;  
		  ps = ConnectionManager.getConnection().prepareStatement(query);  
		  ps.setString(1, cb.getUsername());  
		  ps.setString(2, cb.getPassword());
		  ps.setString(3, cb.getHost());
		  ps.setInt(4, cb.getPort());
		  ps.setString(5, cb.getConnectionname());
		  if(ps.executeUpdate() != 0){
		  	//Close PreparedStatement before fetching new data
		  	ps.close(); 
		  	return true;
		  }  
		  else{
			  ps.close();  
			  return false;
		  }
		  //TO get inserted ID
		} catch (Exception ex) {  
			return false;
		} 
	}

	public boolean removeConnection(FtpConnectionBean cb) {
		String query = "DELETE FROM ftpconnections WHERE connectionname=? AND userid=?";  
		try {  
		  PreparedStatement ps;  
		  ps = ConnectionManager.getConnection().prepareStatement(query);  
		  ps.setString(1, cb.getConnectionname());
		  ps.setInt(2, cb.getUserID());
		  if(ps.executeUpdate() != 0){
		  	//Close PreparedStatement before fetching new data
		  	ps.close(); 
		  	return true;
		  }  
		  else{
			  ps.close();  
			  return false;
		  }
		  //TO get inserted ID
		} catch (Exception ex) {  
			return false;
		} 
	}

	public FtpConnectionBean getItem(int id, String parameter) {
		FtpConnectionBean anItem=null;
		try {  
		  PreparedStatement ps;  
		  ps = ConnectionManager.getConnection().prepareStatement(getItem);  
		  ps.setString(1, parameter);
		  ps.setInt(2, id);
		  ps.execute();
		  ResultSet rs=ps.getResultSet();

		  if(rs.next()){
			   anItem= new FtpConnectionBean();
	          	anItem.setUserID(id);
	          	anItem.setHost(rs.getString("host"));
	              anItem.setPort(rs.getInt("port"));
	              anItem.setUsername(rs.getString("username"));
	              anItem.setPassword(rs.getString("password"));
	              anItem.setConnectionname(rs.getString("connectionname"));
		  	//Close PreparedStatement before fetching new data
		  }
		  ps.close(); 
		} catch (Exception ex) {  
			return null;
		} 
		return anItem;
	}

}
