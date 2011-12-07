package it.unibz.dao;

import it.unibz.model.FavouriteBean;
import it.unibz.util.SQLConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class is responsible for accessing the database for the User's
 * Favourties.
 * 
 * The FavouritesDAO connects to the database using the ConnectionManager class
 * 
 */
public class FavouritesDAO
{
	private static String getItems = "SELECT u.*,f.connectionname FROM usersfavourites u inner join ftpconnections f on f.id=u.connectionid where u.userid=";

	private static String removeItem = "DELETE FROM usersfavourites WHERE connectionid=? AND userid=? AND folderpath like ?";

	private static String removeItembyConnectionname = "DELETE FROM usersfavourites u WHERE connectionid=(SELECT id from ftpconnections where userid=? and connectionname like ?) AND userid=? AND folderpath like ?";

	private static String insertItem = "INSERT INTO usersfavourites (userid,connectionid,folderpath) SELECT userid,id,? from ftpconnections where userid=? and connectionname like ?";
	
	private static String updateItem = "UPDATE usersfavourites SET folderpath=? WHERE userid=? AND connectionid=(SELECT id from ftpconnections where userid=? and connectionname like ?)";

	/**
	 * MEthod returns all Favourties for a specific User as an ArrayList
	 * 
	 * @param userid
	 *          Identifies the User by its ID
	 * @return ArrayList of FavouritesBean
	 */
	public ArrayList<FavouriteBean> getItems(int userid) {
		ArrayList<FavouriteBean> alCatalog = new ArrayList<FavouriteBean>();

		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			// establish a connection to the database via the driver
			con = SQLConnectionManager.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(getItems + userid);
			while (rs.next()) {
				FavouriteBean anItem = new FavouriteBean();
				anItem.setUserID(userid);
				anItem.setConnectionID(rs.getInt("connectionid"));
				anItem.setFolderPATH(rs.getString("folderpath"));
				anItem.setConnectionNAME(rs.getString("connectionname"));
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
	 * Method removes a Favourite from the DB
	 * 
	 * @param cb
	 *          is the Favourite to remove
	 * @return True if succeeded.
	 */
	public boolean removeFavorite(FavouriteBean cb) {

		try {
			PreparedStatement ps;
			ps = SQLConnectionManager.getConnection().prepareStatement(removeItem);
			ps.setInt(1, cb.getConnectionID());
			ps.setInt(2, cb.getUserID());
			ps.setString(3, cb.getFolderPATH());
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
	 * Method adds a Favourite to the DB
	 * 
	 * @param cb
	 *          is the Favourite to add
	 * @return True if succeeded.
	 */
	public boolean addFavourite(FavouriteBean cb) {

		try {
			PreparedStatement ps;
			ps = SQLConnectionManager.getConnection().prepareStatement(insertItem);
			ps.setString(1, cb.getFolderPATH());
			ps.setInt(2, cb.getUserID());
			ps.setString(3, cb.getConnectionNAME());
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
	 * Method checks if Folder already a Favourite in the DB
	 * 
	 * @param cb
	 *          is the Favourite to investigate
	 * @return True if it is a Favourite.
	 */
	public boolean checkFavourite(FavouriteBean cb) {
		String query = "SELECT u.id FROM usersfavourites u inner join ftpconnections f on f.id=u.connectionid where u.userid="
				+ cb.getUserID()
				+ " AND connectionname like '"
				+ cb.getConnectionNAME()
				+ "' "
				+ " AND folderpath like '"
				+ cb.getFolderPATH() + "'";
		boolean ret = false;

		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			// establish a connection to the database via the driver
			con = SQLConnectionManager.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				ret = true;
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
		return ret;
	}

	/**
	 * Method removes a Favourite from the DB by its ConnectionName
	 * 
	 * @param cb
	 *          is the Favourite to remove
	 * @return True if succeeded.
	 */
	public boolean removeFavoriteByConnectionname(FavouriteBean cb) {

		try {
			PreparedStatement ps;
			ps = SQLConnectionManager.getConnection().prepareStatement(
					removeItembyConnectionname);
			ps.setInt(1, cb.getUserID());
			ps.setString(2, cb.getConnectionNAME());
			ps.setInt(3, cb.getUserID());
			ps.setString(4, cb.getFolderPATH());
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
	 * Method changes the path of a favourite to the new one
	 * @param cb Favourtie to modify
	 * @param path new Path
	 */
	public void changeFavouritePath(FavouriteBean cb, String path) {
		try {
			PreparedStatement ps;
			ps = SQLConnectionManager.getConnection().prepareStatement(updateItem);
			ps.setString(1, path);
			ps.setInt(2, cb.getUserID());
			ps.setInt(3, cb.getUserID());
			ps.setString(4, cb.getConnectionNAME());
			if (ps.executeUpdate() != 0) {
				// Close PreparedStatement before fetching new data
				ps.close();
			} else {
				ps.close();
			}
		} catch (Exception ex) {
		}
	}

}
