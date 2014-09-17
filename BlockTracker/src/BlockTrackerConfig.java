import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class BlockTrackerConfig {

	static Properties prop = new Properties();
	static OutputStream output = null;
	public static int[] Blocks;

	
	//Should attempt to ready config and return true if it can.
	//If config is not found it should create one and return false
	public static boolean readConfig() {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			
			try {
			input = new FileInputStream("BlockTrackerDB.config");
			} catch(FileNotFoundException e){
				createConfig();
				return false;
			}

			prop.load(input);

			BlockTracker.host = prop.getProperty("host");
			BlockTracker.database = prop.getProperty("database");
			BlockTracker.dbuser = prop.getProperty("dbuser");
			BlockTracker.dbpass = prop.getProperty("dbpass");
			String var1 = prop.getProperty("blocks");

			
			//Use block names not ID's now.
			//TODO
			String[] items = var1.replaceAll("\\[", "").replaceAll("\\]", "")
					.split(",");
			Blocks = new int[items.length];
			for (int i = 0; i < items.length; i++) {
				try {
					Blocks[i] = Integer.parseInt(items[i]);
				} catch (NumberFormatException nfe) {
					BlockTracker.logger
							.warn("Disabled! Configuration error concerning Blocks",
									nfe);
				}
			}
		} catch (IOException ex) {
			BlockTracker.logger
					.warn("Disabled! Configuration error.", ex);
		} finally {
			if (input != null) {
				try {
					input.close();
					BlockTracker.logger.info("Configuration loaded");
					return true;
				} catch (IOException e) {
					BlockTracker.logger
					.warn("Disabled! Configuration error.", e);
					return false;
				}
			}
		}
		BlockTracker.logger.info("Configuration loaded2");
		return true;
	}

	public static void createConfig() {
		try {

			output = new FileOutputStream("BlockTrackerDB.config");

			// set the properties value
			prop.setProperty("host", "localhost");
			prop.setProperty("database", "blocktracker");
			prop.setProperty("dbuser", "username");
			prop.setProperty("dbpass", "pasword");
			prop.setProperty("blocks", "138,57,54,46,42,41");

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
					BlockTracker.logger.warn("Configuartion file created. Please edit and restart server");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
