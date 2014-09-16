import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//////////
//Geistes's BlockTracker
//Minecraft 1.8 Vanilla
//Under MIT License
//////////

public class BlockTracker {

	private static final Logger logger = LogManager.getLogger();
	static BlockTrackSQL blocktracksql = new BlockTrackSQL();
	public static int[] Blocks;
	private static String host;
	private static String database;
	private static String dbuser;
	private static String dbpass;

	public static void boot() {

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("db.config");

			// load a properties file
			prop.load(input);

			host = prop.getProperty("host");
			database = prop.getProperty("database");
			dbuser = prop.getProperty("dbuser");
			dbpass = prop.getProperty("dbpassword");
			String var1 = prop.getProperty("blocks");

			String[] items = var1.replaceAll("\\[", "").replaceAll("\\]", "")
					.split(",");
			Blocks = new int[items.length];
			for (int i = 0; i < items.length; i++) {
				try {
					Blocks[i] = Integer.parseInt(items[i]);
				} catch (NumberFormatException nfe) {
					logger.warn(
							"BlockTracker disabled! Configuration error concerning Blocks",
							nfe);
				}
			}
		} catch (IOException ex) {
			blocktracksql.createSQL();
			logger.warn("BlockTracker disabled! Configure db.config and restart server.");
		} finally {
			if (input != null) {
				try {
					input.close();
					// System.out.println(time() +
					// "[BlockTracker thread/INFO]: Enabled!");
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
		logger.info("Current Blocks: " + Arrays.toString(Blocks));
	}

	public static void sqlConnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // this accesses Driver in
													// jdbc.
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://"
						+ host + "/" + database, dbuser, dbpass);
			} catch (SQLException e) {
				logger.warn("BlockTracker disabled: SQL error occurred.", e);
			}
		} catch (ClassNotFoundException e) {
			logger.warn("BlockTracker disabled: mySQL dependency issue.", e);
		}
	}

}
