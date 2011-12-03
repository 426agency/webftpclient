package it.unibz.controller;

import it.unibz.model.FavouriteBean;
import it.unibz.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class FavouritesDAO {
	private static String getItems = "SELECT u.*,f.connectionname FROM usersfavourites u inner join ftpconnections f on f.id=u.connectionid where u.userid=";
	 public ArrayList<FavouriteBean> getItems(int userid) {
	      ArrayList<FavouriteBean> alCatalog = new ArrayList<FavouriteBean>();

	      Statement stmt = null;
	      ResultSet rs = null;
	      Connection con = null;

	      try {
	          // establish a connection to the database via the driver
	          con = ConnectionManager.getConnection();
	          stmt = con.createStatement();
	          rs = stmt.executeQuery(getItems+userid);
	          while (rs.next()) {
	        	  FavouriteBean anItem = new FavouriteBean();
	          	anItem.setUserID(userid);
	          	anItem.setConnectionID(rs.getInt("connectionid"));
	              anItem.setFolderPATH(rs.getString("folderpath"));
	              anItem.setConnectionNAME(rs.getString("connectionname"));
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
	public boolean removeFavorite(FavouriteBean cb) {
		String query = "DELETE FROM usersfavourites WHERE connectionid=? AND userid=? AND folderpath like ?";  
		try {  
		  PreparedStatement ps;  
		  ps = ConnectionManager.getConnection().prepareStatement(query);  
		  ps.setInt(1, cb.getConnectionID());
		  ps.setInt(2, cb.getUserID());
		  ps.setString(3, cb.getFolderPATH());
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
	public boolean addFavourite(FavouriteBean cb) {
		String query = "INSERT INTO usersfavourites (userid,connectionid,folderpath) SELECT userid,id,? from ftpconnections where userid=? and connectionname like ?";  
		try {  
		  PreparedStatement ps;  
		  ps = ConnectionManager.getConnection().prepareStatement(query);  
		  ps.setString(1, cb.getFolderPATH());  
		  ps.setInt(2, cb.getUserID());
		  ps.setString(3, cb.getConnectionNAME());
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
	public boolean checkFavourite(FavouriteBean cb) {
		String query = "SELECT u.id FROM usersfavourites u inner join ftpconnections f on f.id=u.connectionid where u.userid="+cb.getUserID()+" AND connectionname like '"+cb.getConnectionNAME()+"' " +
				" AND folderpath like '/"+cb.getFolderPATH()+"'";
		boolean ret=false;

    Statement stmt = null;
    ResultSet rs = null;
    Connection con = null;

    try {
        // establish a connection to the database via the driver
        con = ConnectionManager.getConnection();
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        if (rs.next()) {
      	 ret=true;
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
    return ret;
	}
	public boolean removeFavoriteByConnectionname(FavouriteBean cb) {
		String query = "DELETE FROM usersfavourites u WHERE connectionid=(SELECT id from" +
				" ftpconnections where userid=? and connectionname like ?) AND userid=? AND folderpath like ?";  
		try {  
		  PreparedStatement ps;  
		  ps = ConnectionManager.getConnection().prepareStatement(query);  
		  ps.setInt(1, cb.getUserID());
		  ps.setString(2, cb.getConnectionNAME());
		  ps.setInt(3, cb.getUserID());
		  ps.setString(4, cb.getFolderPATH());
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

}
