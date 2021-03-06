package RainbowBlockTracker;

import PluginReference.ChatColor;
import PluginReference.MC_DimensionType;
import PluginReference.MC_Player;
import PluginReference.MC_World;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
	public synchronized static Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			MyPlugin.logger.warning("Disabled");
			MyPlugin.logger.warning("mySQL dependencies error" + e.getMessage());
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + MyPlugin.host,
                    MyPlugin.dbuser, MyPlugin.dbpass);
		} catch (SQLException err) {
			MyPlugin.logger.warning("Disabled");
			MyPlugin.logger.warning("mySQL connection error" + err.getMessage());
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
			String sql = "CREATE DATABASE `" + MyPlugin.database + "`;";
			statement.executeUpdate(sql);
		} catch (SQLException sqlException) {
			if (sqlException.getErrorCode() == 1007) {
				// Database already exists error
				closeStatement(statement);
				closeConnection(connection);
				MyPlugin.logger.info("Database: OK");
				return true;
			} else {
				MyPlugin.logger.warning("BlockTracker Disabled!" + sqlException.getMessage());
				closeStatement(statement);
				closeConnection(connection);
				return false;
			}
		}
		// Database created
		MyPlugin.logger.info("Database: OK");
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
			String sql = "USE `" + MyPlugin.database + "`;";
			statement.execute(sql);
			// Create Table
			String createTable = "CREATE TABLE IF NOT EXISTS `" + MyPlugin.database + "`.`" + MyPlugin.dbtable + "` ("
					+ "`UID` INT NOT NULL AUTO_INCREMENT, "
					+ "`player` VARCHAR(45) NOT NULL, "
                    + "`world` VARCHAR(45) NOT NULL, "
					+ "`x` VARCHAR(45) NOT NULL, "
					+ "`y` VARCHAR(45) NOT NULL, "
					+ "`z` VARCHAR(45) NOT NULL, "
					+ "`time` VARCHAR(45) NOT NULL, "
					+ "`block` VARCHAR(255) NOT NULL, "
					+ "`event` VARCHAR(45) NOT NULL, "
					+ "PRIMARY KEY (`UID`));";
			statement.execute(createTable);
		} catch (SQLException e) {
			MyPlugin.logger.warning("Disabled!");
			MyPlugin.logger.warning("mySQL table related error" + e.getMessage());
			closeConnection(connection);
			closeStatement(statement);
			return false;
		}
		// Table exists
		closeConnection(connection);
		closeStatement(statement);
		MyPlugin.logger.info("Tables OK");
		return true;
	}

	public static boolean insertBlockEvent(String player, int world, int x, int y, int z, String time, String block, String event) {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			String SelectDB = "USE `" + MyPlugin.database + "`;";
			String Insert = "INSERT INTO `" + MyPlugin.dbtable + "` (`player`, `world`, `x`, `y`, `z`, `time`, `block`, `event`) VALUES ('"
					+ player
					+ "', '"
                    + world
                    + "', '"
					+ x
					+ "', '"
					+ y
					+ "', '"
					+ z
					+ "', '"
					+ time
					+ "', \""
					+ block
					+ "\", '"
					+ event
					+ "');";
			statement.execute(SelectDB);
			statement.execute(Insert);
		} catch (SQLException sqlException) {
			MyPlugin.logger.warning("BlockTracker Error!" + sqlException.getMessage());
			closeStatement(statement);
			closeConnection(connection);
			return false;
		}
		closeStatement(statement);
		closeConnection(connection);
		return true;
	}

	public static void getBlockRecord(MC_Player plr, int World, int X, int Y, int Z) {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs;
        int start = 0;//page * 10;
        int end = start + 10;
        List<String> results = new ArrayList<String>();
		
		 try{
			 connection = getConnection();
				statement = connection.createStatement();
				String SelectDB = "USE `" + MyPlugin.database + "`;";
				String Fetch = "SELECT * FROM `" + MyPlugin.dbtable + "`" +
                        "WHERE `world`='" + World + "'" +
                        "AND `x`='" + X + "'" +
                        "AND `y`='" + Y + "'" +
                        "AND `z`='" + Z + "'" +
                        "ORDER BY UID DESC LIMIT " + start + ", " + end + ";";
				statement.execute(SelectDB);
				rs = statement.executeQuery(Fetch); 

		        while (rs.next()) {
                    results.add(ChatColor.GREEN + rs.getString("time") + " " +
                            ChatColor.AQUA + rs.getString("player") + " " +
                            ChatColor.RED + rs.getString("event") + " " +
                            ChatColor.GOLD + rs.getString("block"));
		        }
             plr.sendMessage(ChatColor.DARK_PURPLE + "Block changes at " + X + ", " + Y + ", " + Z);
             Collections.sort(results);
             for(int i = 0; i < results.size(); i++){
                 plr.sendMessage(results.get(i));
             }

        } catch(SQLException ex){
            plr.sendMessage("Error: " + ex.getMessage());
        }
		
		closeConnection(connection);
		closeStatement(statement);
	}

	public static void lookupPlayer(MC_Player player, String targetPlayer, int page) {

		Connection connection = null;
		Statement statement = null;
		ResultSet rs;
		int start = page * 10;
		int end = start + 10;
		List<String> results = new ArrayList<String>();

		try{
			connection = getConnection();
			statement = connection.createStatement();
			String SelectDB = "USE `" + MyPlugin.database + "`;";
			String Fetch = "SELECT * FROM `" + MyPlugin.dbtable + "` " +
					"WHERE `player` LIKE '%" + targetPlayer + "%' " +
					"ORDER BY UID DESC LIMIT " + start + ", " + end + ";";
			statement.execute(SelectDB);
			rs = statement.executeQuery(Fetch);

			while (rs.next()) {
				results.add(ChatColor.GREEN + rs.getString("time") + " " +
						ChatColor.AQUA + rs.getString("world") + " (" +
						rs.getString("x") + ", " + rs.getString("y") + ", " + rs.getString("z") + ") " +
						ChatColor.RED + rs.getString("event") + " " +
						ChatColor.GOLD + rs.getString("block"));
			}
			player.sendMessage(ChatColor.DARK_PURPLE + "Block changes by player " + targetPlayer);
			Collections.sort(results);
			for(int i = 0; i < results.size(); i++){
				player.sendMessage(results.get(i));
			}

		} catch(SQLException ex){
			player.sendMessage("Error: " + ex.getMessage());
		}

		closeConnection(connection);
		closeStatement(statement);
	}

	public static void lookupCoord(MC_Player plr, int World, int X, int Y, int Z, int Radius, int page) {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs;
		int start = page * 10;
		int end = start + 10;
		List<String> results = new ArrayList<String>();

		try{
			connection = getConnection();
			statement = connection.createStatement();
			String SelectDB = "USE `" + MyPlugin.database + "`;";
			String Fetch = "SELECT * FROM `" + MyPlugin.dbtable + "` " +
					"WHERE (`world`='" + World + "' " +
					"AND (`x` BETWEEN '" + (X - Radius) + "' AND '" + (X + Radius) + "') " +
					"AND (`y` BETWEEN '" + (Y - Radius) + "' AND '" + (Y + Radius) + "') " +
					"AND (`z` BETWEEN '" + (Z - Radius) + "' AND '" + (Z + Radius) + "')) " +
					"ORDER BY UID DESC LIMIT " + start + ", " + end + ";";
			statement.execute(SelectDB);
			rs = statement.executeQuery(Fetch);

			while (rs.next()) {
				results.add(ChatColor.GREEN + rs.getString("time") + " " +
						ChatColor.AQUA + rs.getString("player") + " " +
						ChatColor.RED + rs.getString("event") + " " +
						ChatColor.GOLD + rs.getString("block"));
			}
			plr.sendMessage(ChatColor.DARK_PURPLE + "Block changes within " + Radius + " blocks of " + World + " (" + X + ", " + Y + ", " + Z + ")");
			Collections.sort(results);
			for(int i = 0; i < results.size(); i++){
				plr.sendMessage(results.get(i));
			}

		} catch(SQLException ex){
			plr.sendMessage("Error: " + ex.getMessage());
		}

		closeConnection(connection);
		closeStatement(statement);
	}

	public static void lookupRecent(MC_Player player, int page) {

		Connection connection = null;
		Statement statement = null;
		ResultSet rs;
		int start = page * 10;
		int end = start + 10;
		List<String> results = new ArrayList<String>();

		try{
			connection = getConnection();
			statement = connection.createStatement();
			String SelectDB = "USE `" + MyPlugin.database + "`;";
			String Fetch = "SELECT * FROM `" + MyPlugin.dbtable + "` " +
					"ORDER BY UID DESC LIMIT " + start + ", " + end + ";";
			statement.execute(SelectDB);
			rs = statement.executeQuery(Fetch);

			while (rs.next()) {
				results.add(ChatColor.GREEN + rs.getString("time") + " " +
						ChatColor.AQUA + rs.getString("world") + " (" +
						rs.getString("x") + ", " + rs.getString("y") + ", " + rs.getString("z") + ") " +
						ChatColor.GRAY + rs.getString("player") + " " +
						ChatColor.RED + rs.getString("event") + " " +
						ChatColor.GOLD + rs.getString("block"));
			}
			player.sendMessage(ChatColor.DARK_PURPLE + "Recent block changes");
			Collections.sort(results);
			for(int i = 0; i < results.size(); i++){
				player.sendMessage(results.get(i));
			}

		} catch(SQLException ex){
			player.sendMessage("Error: " + ex.getMessage());
		}

		closeConnection(connection);
		closeStatement(statement);
	}

	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			MyPlugin.logger.warning("mySQL error: Could not close connection" + e.getMessage());
		}
	}

	public static void closeStatement(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			MyPlugin.logger.warning("mySQL error: Could not close statement" + e.getMessage());
		}
	}

}