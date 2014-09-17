import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
	static BlockTrackerSQL blocktracksql = new BlockTrackerSQL();
	public static int[] Blocks;
	public static String host;
	public static String database;
	public static String dbuser;
	public static String dbpass;
	private static String fr;

	public static void boot() {

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("BlockTrackerDB.config");

			// load a properties file
			prop.load(input);

			host = (prop.getProperty("host"));
			database = (prop.getProperty("database"));
			dbuser = prop.getProperty("dbuser");
			dbpass = prop.getProperty("dbpassword");
			fr = prop.getProperty("fr");
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
					// logger.info("Current Monitoring Blocks: " + Arrays.toString(Blocks));
					// "[BlockTracker thread/INFO]: Enabled!");
					initDB();
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

	public static void initDB() {
		if(fr.equals(1)){
		Connection conn = null;
		try {
			conn = BlockTrackerSQL.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.createStatement().execute("CREATE SCHEMA `blocktracker` ;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}else{
	}
	}

}