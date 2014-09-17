import java.io.OutputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BlockTrackerSQL {
	
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
			conn = DriverManager.getConnection("jdbc:mysql://" + BlockTracker.host, BlockTracker.dbuser,
					BlockTracker.dbpass);
		} catch (SQLException err) {
			BlockTracker.logger.warn("Disabled");
			BlockTracker.logger.warn("mySQL connection error", err);
		}
		BlockTracker.logger.info("Attempted to make SQL Connection.");
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
			String sql = "CREATE DATABASE " + BlockTracker.database + ";";
			statement.executeUpdate(sql);
		} catch (SQLException sqlException) {
			if (sqlException.getErrorCode() == 1007) {
				// Database already exists error
				closeStatement(statement);
				closeConnection(connection);
				BlockTracker.logger.info("Database Exists!");
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
		BlockTracker.logger.info("Database created!");
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
			String sql = "USE " + BlockTracker.database + ";";
			statement.execute(sql);
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "blockbreaks", null);
			if (tables.next()) {
				// Table exists
				closeConnection(connection);
				closeStatement(statement);
				BlockTracker.logger.info("Table exists.");
				return true;
			} else {
				// Table does not exist
				// Create Table
				BlockTracker.logger.info("Table does not exist, creating...");
				String createTable = "CREATE TABLE `blocktracker`.`blockbreaks` (" +
						  "`UID` INT NOT NULL AUTO_INCREMENT, " +
						  "`player` VARCHAR(45) NOT NULL, " +
						  "`x` VARCHAR(45) NOT NULL, " +
						  "`y` VARCHAR(45) NOT NULL, " +
						  "`z` VARCHAR(45) NOT NULL, " +
						  "`world` VARCHAR(45) NOT NULL, " +
						  "`time` VARCHAR(45) NOT NULL, " +
						  "`block` VARCHAR(45) NOT NULL, " +
						  "`variant` VARCHAR(45) NOT NULL, " +
						  "PRIMARY KEY (`UID`));";
				statement.execute(createTable);
			}
		} catch (SQLException e) {
			BlockTracker.logger.warn("Disabled!");
			BlockTracker.logger.warn("mySQL table related error", e);
			closeConnection(connection);
			closeStatement(statement);
			return false;
		}
		// Table exists
		closeConnection(connection);
		closeStatement(statement);
		BlockTracker.logger.info("Tables Created");
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
