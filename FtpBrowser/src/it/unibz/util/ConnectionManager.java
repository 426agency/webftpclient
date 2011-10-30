package it.unibz.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;



public class ConnectionManager {
    
    static Connection con;
	static String url;
        
	public static String getWebServiceResponse(String[] params){
		String url = "http://search.yahoo.com/search";
		   
			   HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("http://vogellac2dm.appspot.com/register");
		   String ret=null;

				try {
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
					nameValuePairs.add(new BasicNameValuePair("registrationid",
							"123456789"));
					post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		 
					HttpResponse response = client.execute(post);
					BufferedReader rd = new BufferedReader(new InputStreamReader(
							response.getEntity().getContent()));
					String line = "";
					while ((line = rd.readLine()) != null) {
						ret+=line;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
				return ret;
		
	}
    
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
