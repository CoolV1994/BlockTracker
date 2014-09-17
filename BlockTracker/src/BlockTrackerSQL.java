import java.io.OutputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BlockTrackerSQL {

	public static String host = BlockTrackerConfig.host;
	public static String database = BlockTrackerConfig.database;
	public static String dbuser = BlockTrackerConfig.dbuser;
	public static String dbpass = BlockTrackerConfig.dbpass;
	Properties prop = new Properties();
	OutputStream output = null;

	public synchronized static Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			BlockTracker.logger.warn("Disabled");
			BlockTracker.logger.warn("mySQL dependencies error", e);
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + host, dbuser,
					dbpass);
		} catch (SQLException err) {
			BlockTracker.logger.warn("Disabled");
			BlockTracker.logger.warn("mySQL connection error", err);
		}
		return conn;
	}

	
	//Returns true if DB exists or if it has been created.
	//Returns false if error, should stop mod.
	//Server should continue to run.
	public static boolean checkDB() {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			String sql = "CREATE DATABASE " + database;
			statement.executeUpdate(sql);
			BlockTracker.logger.info("Database created!");
		} catch (SQLException sqlException) {
			if (sqlException.getErrorCode() == 1007) {
				// Database already exists error
				closeStatement(statement);
				closeConnection(connection);
				return true;
			} else {
				BlockTracker.logger
						.warn("BlockTracker Disabled!", sqlException);
				closeStatement(statement);
				closeConnection(connection);
				return false;
			}
		}
		// Database created
		BlockTracker.logger.info("Database Exists!");
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	
	//Returns true if Table exists or if it has been created.
	//Returns false if error, should stop mod.
	//Server should continue to run.
	public static boolean checkTable() {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			String sql = "SELECT DATABASE " + database;
			statement.executeUpdate(sql);
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "BlockTracking", null);
			if (tables.next()) {
				// Table exists
				closeConnection(connection);
				closeStatement(statement);
				return true;
			} else {
				// Table does not exist
				// Create Table
			}
		} catch (SQLException e) {
			BlockTracker.logger.warn("sDisabled!");
			BlockTracker.logger.warn("mySQL table related error", e);
			closeConnection(connection);
			closeStatement(statement);
			return false;
		}
		// Table exists
		closeConnection(connection);
		closeStatement(statement);
		return true;
	}

	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			BlockTracker.logger.warn("mySQL error: Could not close connection", e);
		}
	}

	public static void closeStatement(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			BlockTracker.logger.warn("mySQL error: Could not close statement", e);
		}
	}

}
