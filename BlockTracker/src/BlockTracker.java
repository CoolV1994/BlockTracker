import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BlockTracker{

	private static final Logger logger = LogManager.getLogger();
	static BlockTrackSQL blocktracksql = new BlockTrackSQL();
	private static String database;
	private static String schema;
	private static String dbuser;
	private static String dbpass;


	public static void boot(){

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("db.config");

			// load a properties file
			prop.load(input);

			database = prop.getProperty("database");
			schema = prop.getProperty("schema");
			dbuser = prop.getProperty("dbuser");
			dbpass = prop.getProperty("dbpassword");

		} catch (IOException ex) {
			blocktracksql.createSQL();
			logger.warn("Block Tracker disabled! Configure db.config and restart server.");
		} finally {
			if (input != null) {
				try {
					input.close();
					//System.out.println(time() + "[BlockTracker thread/INFO]: Enabled!");
					sqlConnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void BlockBreakEvent(aqu var1, dt var2, bec var3, ahd var4) {
		logger.warn("BlockBreakEvent");
		logger.info("AQU: " + var1);
		logger.info("DT: " + var2);
		logger.info("BEC: " + var3);
		logger.info("AHD: " + var4);
	}

	public static void sqlConnect(){
		try{
			Class.forName("com.mysql.jdbc.Driver"); //this accesses Driver in jdbc.
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://" + database + "/" + schema, dbuser, dbpass);
			} catch (SQLException e) {
				logger.warn("Block Tracker disabled: SQL error occurred.", e);
			}
		} catch (ClassNotFoundException e) {
			logger.warn("Block Tracker disabled: Unable to connect to database.", e);
		}
	}

}
