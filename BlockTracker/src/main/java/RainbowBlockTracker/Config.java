package RainbowBlockTracker;

import java.io.*;
import java.util.Properties;

public class Config {

    static Properties prop = new Properties();
    static OutputStream output = null;
    static File config = new File("plugins_mod/RainbowBlockTracker/config.txt");

    public static boolean readConfig() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            try {
                input = new FileInputStream(config);
            } catch (FileNotFoundException e) {
                createConfig();
                return false;
            }

            prop.load(input);

            MyPlugin.host = prop.getProperty("host");
            MyPlugin.database = prop.getProperty("database");
            MyPlugin.dbuser = prop.getProperty("dbuser");
            MyPlugin.dbpass = prop.getProperty("dbpass");
            MyPlugin.dbtable = prop.getProperty("dbtable", "blockbreaks");
            MyPlugin.queueThreads = Integer.parseInt(prop.getProperty("queueThreads", "2"));
            MyPlugin.queueShutdownTimeout = Long.parseLong(prop.getProperty("queueShutdownTimeout", "60"));
        } catch (IOException ex) {
            MyPlugin.logger.warning("Disabled! Configuration error." + ex.getMessage());
        }
        try {
            input.close();
        } catch (IOException e) {
            MyPlugin.logger.warning("Disabled! Configuration error." + e.getMessage());
            return false;
        }
        MyPlugin.logger.info("Config: OK");
        return true;
    }

    public static void createConfig() {
        try {
            config.getParentFile().mkdirs();

            output = new FileOutputStream(config);

            prop.setProperty("host", "localhost");
            prop.setProperty("database", "blocktracker");
            prop.setProperty("dbuser", "username");
            prop.setProperty("dbpass", "password");
            prop.setProperty("dbtable", "blockbreaks");
            prop.setProperty("queueThreads", "2");
            prop.setProperty("queueShutdownTimeout", "60");

            prop.store(output, "RainbowBlockTracker Configuration");

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                    MyPlugin.logger.warning("Configuration file created. Please edit and restart server");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}

