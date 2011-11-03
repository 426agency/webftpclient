package it.unibz.util;
import java.sql.*;



public class ConnectionManager {
    
    static Connection con;
	static String url;
        
	
    
    public static Connection getConnection()
    {
        
        try
        {
        	Class.forName("org.postgresql.Driver");
            
            try
            {            	
                con = DriverManager.getConnection("jdbc:postgresql://mysql.alwaysdata.com:5432/ftpunibzteam_main","ftpunibzteam","thehons88"); 
                										// Assuming your SQL Server's	username is "username"
                										// And password is "password"
                 
            }
            
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e);
        }
        return con;
    }

}
