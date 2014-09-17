import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BlockTrackerSQL implements Closeable {

	public static String host = BlockTracker.host;
	public static String database = BlockTracker.database;
	public static String dbuser = BlockTracker.dbuser;
	public static String dbpass = BlockTracker.dbpass;
	Properties prop = new Properties();
	OutputStream output = null;

	public boolean createSQL() {
		try {

			output = new FileOutputStream("BlockTrackerDB.config");

			// set the properties value
			prop.setProperty("host", "localhost");
			prop.setProperty("database", "blocktracker");
			prop.setProperty("dbuser", "username");
			prop.setProperty("dbpassword", "password");
			prop.setProperty("fr", "1");
			prop.setProperty("blocks", "138,57,54,46,42,41");

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
			return false;
		} finally {
			if (output != null) {
				try {
					output.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}

		}
		return false;
	}

	public synchronized static Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try{
		conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, dbuser, dbpass);
		 }
		 catch (Exception err){
		 }
		return conn;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
