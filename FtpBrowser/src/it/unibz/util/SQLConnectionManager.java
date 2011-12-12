package it.unibz.util;

import java.sql.*;
import java.util.Hashtable;
import java.util.Properties;
import com.protomatter.jdbc.pool.*;

/**
 * Class represents the Postgres DB Connector
 * 
 * Self explanatory
 * 
 */
public class SQLConnectionManager
{

	static Connection con;

	static String url;

	private static final String driverName = "org.postgresql.Driver";

	private static final String user = "ftpunibzteam";

	private static final String passwd = "thehons88";

	private static final String dburl = "jdbc:postgresql://mysql.alwaysdata.com:5432/ftpunibzteam_main";

	private static final int LOGIN_TIMEOUT = 5;

	private static JdbcConnectionPool pool;

	// Create static connection pooling
	static {
		try {
			Hashtable<String, Object> args = new Hashtable<String, Object>();
			args.put("jdbc.driver", driverName);
			args.put("jdbc.URL", dburl);
			args.put("jdbc.validityCheckStatement", "SELECT 1;");
			args.put("pool.validateOnCheckout", new Boolean(true));
			args.put("pool.maxCheckoutRefreshAttempts", new Integer(1));

			Properties jdbcProperties = new Properties();
			jdbcProperties.setProperty("user", user);
			jdbcProperties.setProperty("password", passwd);

			args.put("jdbc.properties", jdbcProperties);

			pool = new JdbcConnectionPool("FtpBrowserDB", args);

			DriverManager.setLoginTimeout(LOGIN_TIMEOUT);
		} catch (Exception e) {
			// Do something within...
			//System.out.println(e.getMessage());
		}
	}

	public static Connection getConnection() throws Exception {
		Connection con = DriverManager
				.getConnection("jdbc:protomatter:pool:FtpBrowserDB");
		return con;
	}

	public static JdbcConnectionPool getPool() {
		return pool;
	}

}
