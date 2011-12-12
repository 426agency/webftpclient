package it.unibz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import it.unibz.model.*;
import it.unibz.util.SQLConnectionManager;

/**
 * This class is responsible for accessing the database for the User's
 * Connections.
 * 
 * The FtpConnectionDAO connects to the database using the ConnectionManager
 * class
 * 
 */
public class FtpConnectionDAO
{
	private static String getItems = "SELECT * FROM ftpconnections where userid=";

	private static String getItem = "SELECT * FROM ftpconnections where connectionname=? AND userid=?";

	private static String insertItem = "INSERT INTO ftpconnections (username,password,host,port,userid,connectionname) VALUES(?,?,?,?,?,?)";

	private static String updateItem = "UPDATE ftpconnections SET username=?,password=?,host=?,port=? WHERE connectionname=?";

	private static String removeItem = "DELETE FROM ftpconnections WHERE connectionname=? AND userid=?";

	/**
	 * Method returns all Connections for a specific User as an ArrayList
	 * 
	 * @param userid
	 *          Identifies the User by its ID
	 * @return ArrayList of FtpConnectionBean
	 */
	public ArrayList<FtpConnectionBean> getItems(int userid) {
		ArrayList<FtpConnectionBean> alCatalog = new ArrayList<FtpConnectionBean>();

		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			// establish a connection to the database via the driver
			con = SQLConnectionManager.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(getItems + userid);
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
			System.out.println("Exception executing query: " + e.getMessage());
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				System.out.println("Exception closing rs: " + e.getMessage());
			}
			try {
				stmt.close();
			} catch (Exception e) {
				System.out.println("Exception closing stmt: " + e.getMessage());
			}
			try {
				con.close();
			} catch (Exception e) {
				System.out.println("Exception closing con: " + e.getMessage());
			}
		}
		return alCatalog;
	}

	/**
	 * Method inserts a new Connection in the DB
	 * 
	 * @param cb
	 *          is the ConnectionBEan to insert
	 * @return true if succeeded
	 */
	public boolean createConnection(FtpConnectionBean cb) {

		try {
			PreparedStatement ps;
			ps = SQLConnectionManager.getConnection().prepareStatement(insertItem);
			ps.setString(1, cb.getUsername());
			ps.setString(2, cb.getPassword());
			ps.setString(3, cb.getHost());
			ps.setInt(4, cb.getPort());
			ps.setInt(5, cb.getUserID());
			ps.setString(6, cb.getConnectionname());
			if (ps.executeUpdate() != 0) {
				// Close PreparedStatement before fetching new data
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			}
			// TO get inserted ID
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Method updates the Connection entry in the DB
	 * 
	 * @param cb
	 *          is the ConnectionBean to update
	 * @return true if succeeded
	 */
	public boolean editConnection(FtpConnectionBean cb) {
		try {
			PreparedStatement ps;
			ps = SQLConnectionManager.getConnection().prepareStatement(updateItem);
			ps.setString(1, cb.getUsername());
			ps.setString(2, cb.getPassword());
			ps.setString(3, cb.getHost());
			ps.setInt(4, cb.getPort());
			ps.setString(5, cb.getConnectionname());
			if (ps.executeUpdate() != 0) {
				// Close PreparedStatement before fetching new data
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			}
			// TO get inserted ID
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Method removes a Connection from the DB
	 * 
	 * @param cb
	 *          is the Connection to remove
	 * @return True if succeeded.
	 */
	public boolean removeConnection(FtpConnectionBean cb) {
		try {
			PreparedStatement ps;
			ps = SQLConnectionManager.getConnection().prepareStatement(removeItem);
			ps.setString(1, cb.getConnectionname());
			ps.setInt(2, cb.getUserID());
			if (ps.executeUpdate() != 0) {
				// Close PreparedStatement before fetching new data
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			}
			// TO get inserted ID
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Method returns a single ConnectionBean for specific User by its
	 * Connectionname
	 * 
	 * @param id
	 *          User ID
	 * @param conname
	 *          is the Connections 'Name'
	 * @return True if succeeded
	 */
	public FtpConnectionBean getItem(int id, String conname) {
		FtpConnectionBean anItem = null;
		try {
			PreparedStatement ps;
			ps = SQLConnectionManager.getConnection().prepareStatement(getItem);
			ps.setString(1, conname);
			ps.setInt(2, id);
			ps.execute();
			ResultSet rs = ps.getResultSet();

			if (rs.next()) {
				anItem = new FtpConnectionBean();
				anItem.setUserID(id);
				anItem.setHost(rs.getString("host"));
				anItem.setPort(rs.getInt("port"));
				anItem.setUsername(rs.getString("username"));
				anItem.setPassword(rs.getString("password"));
				anItem.setConnectionname(rs.getString("connectionname"));
				// Close PreparedStatement before fetching new data
			}
			ps.close();
		} catch (Exception ex) {
			return null;
		}
		return anItem;
	}

}
