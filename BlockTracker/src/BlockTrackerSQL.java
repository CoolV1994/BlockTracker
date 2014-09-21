import java.io.OutputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


//TODO
//Need to resdesign table to allow for different events
//BlockBreak
//BlockPlace
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
			conn = DriverManager.getConnection("jdbc:mysql://"
					+ BlockTracker.host, BlockTracker.dbuser,
					BlockTracker.dbpass);
		} catch (SQLException err) {
			BlockTracker.logger.warn("Disabled");
			BlockTracker.logger.warn("mySQL connection error", err);
		}
		return conn;
	}

	// Returns true if DB exists or if it has been created.
	// Returns false if error.
	// Server should continue to run.
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
				BlockTracker.logger.info("Database: OK");
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
		BlockTracker.logger.info("Database: OK");
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	// Returns true if Table exists or if it has been created.
	// Server should continue to run.
	public static boolean checkTable() {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			String sql = "USE " + BlockTracker.database + ";";
			statement.execute(sql);
			// Create Table
			String createTable = "CREATE TABLE IF NOT EXISTS `blocktracker`.`blockbreaks` ("
					+ "`UID` INT NOT NULL AUTO_INCREMENT, "
					+ "`player` VARCHAR(45) NOT NULL, "
					+ "`x` VARCHAR(45) NOT NULL, "
					+ "`y` VARCHAR(45) NOT NULL, "
					+ "`z` VARCHAR(45) NOT NULL, "
					+ "`time` VARCHAR(45) NOT NULL, "
					+ "`block` VARCHAR(45) NOT NULL, "
					+ "PRIMARY KEY (`UID`));";
			statement.execute(createTable);
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
		BlockTracker.logger.info("Tables OK");
		return true;
	}

	public static boolean insertBlockBreak(String player, String x, String y,
			String z, String time, String block) {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			String SelectDB = "USE " + BlockTracker.database + ";";
			String Insert = "INSERT INTO `blockbreaks` (`player`, `x`, `y`, `z`, `time`, `block`) VALUES ('"
					+ player
					+ "', '"
					+ x
					+ "', '"
					+ y
					+ "', '"
					+ z
					+ "', '"
					+ time + "', '" + block + "'" + ")" + ";";
			statement.execute(SelectDB);
			statement.execute(Insert);
		} catch (SQLException sqlException) {
			BlockTracker.logger.warn("BlockTracker Disabled!", sqlException);
			closeStatement(statement);
			closeConnection(connection);
			return false;
		}
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			BlockTracker.logger.warn("mySQL error: Could not close connection",
					e);
		}
	}

	public static void closeStatement(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			BlockTracker.logger.warn("mySQL error: Could not close statement",
					e);
		}
	}

}
