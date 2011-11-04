package it.unibz.controller;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import it.unibz.model.*;
import it.unibz.util.ConnectionManager;

public class FtpConnectionDAO
{
	private static String getItems = "SELECT * FROM ftpconnections where userid=";

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
