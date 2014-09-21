
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

    public static boolean readConfig() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            try {
                input = new FileInputStream("BlockTrackerDB.config");
            } catch (FileNotFoundException e) {
                createConfig();
                return false;
            }

            prop.load(input);

            BlockTracker.host = prop.getProperty("host");
            BlockTracker.database = prop.getProperty("database");
            BlockTracker.dbuser = prop.getProperty("dbuser");
            BlockTracker.dbpass = prop.getProperty("dbpass");
        } catch (IOException ex) {
            BlockTracker.logger.warn("Disabled! Configuration error.", ex);
        }
        try {
            input.close();
        } catch (IOException e) {
            BlockTracker.logger.warn("Disabled! Configuration error.", e);
            return false;
        }
        BlockTracker.logger.info("Config: OK");
        return true;
    }

    public static void createConfig() {
        try {

            output = new FileOutputStream("BlockTrackerDB.config");

            prop.setProperty("host", "localhost");
            prop.setProperty("database", "blocktracker");
            prop.setProperty("dbuser", "username");
            prop.setProperty("dbpass", "pasword");

            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                    BlockTracker.logger
                            .warn("Configuration file created. Please edit and restart server");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}

