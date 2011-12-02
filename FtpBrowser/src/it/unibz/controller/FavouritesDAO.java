package it.unibz.controller;

import it.unibz.model.FavouriteBean;
import it.unibz.util.ConnectionManager;

import java.sql.Connection;
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

}
