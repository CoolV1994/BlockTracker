import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//////////
//Geistes's BlockTracker
//Minecraft 1.8 Vanilla
//Under MIT License
//////////

public class BlockTracker extends Thread {

	public static final Logger logger = LogManager.getLogger();
	public static String host;
	public static String database;
	public static String dbuser;
	public static String dbpass;
	public static int[] Blocks;
	public static boolean Track;
	boolean ready;

	public synchronized void run() {
		this.setName("BlockTracker");
		logger.info("BlockTracker 1.0");
		logger.info("Server: v1.8");
		logger.info("-Gesites <3-");
		logger.info("Checking Config...");
		if (BlockTrackerConfig.readConfig() == true) {
			logger.info("Checking SQL DB...");
			if (BlockTrackerSQL.checkDB() == true) {
				logger.info("Checking SQL Table...");
				if (BlockTrackerSQL.checkTable() == true) {
					logger.info("Everything appears OK");
					logger.info("Enabled!");
				} else {
					Track = false;
				}
			} else {
				Track = false;
			}
		} else {
			Track = false;
		}
	}

	public void BlockBreakEvent(aqu var1, dt var2, bec var3, ahd var4) {
		if (Track == true) {
			String BlockType = String.valueOf(var3);

			String BlockCoordsRaw = String.valueOf(var2);
			String BlockCoordsLessRaw = BlockCoordsRaw.substring(5);
			BlockCoordsLessRaw = BlockCoordsLessRaw.replace(",", "");
			BlockCoordsLessRaw = BlockCoordsLessRaw.replace("}", "");
			BlockCoordsLessRaw = BlockCoordsLessRaw.replace("=", "");
			BlockCoordsLessRaw = BlockCoordsLessRaw.replace("y", "");
			BlockCoordsLessRaw = BlockCoordsLessRaw.replace("z", "");
			BlockCoordsLessRaw = BlockCoordsLessRaw.replace(" ", "/");
			String[] BlockCoordsArray = BlockCoordsLessRaw.split("/");
			String X = BlockCoordsArray[0];
			String Y = BlockCoordsArray[1];
			String Z = BlockCoordsArray[2];

			String PlayerRaw = String.valueOf(var4);
			String[] PlayerSplitArray = PlayerRaw.split("/");
			String PlayerLessRaw = PlayerSplitArray[0];
			String QuotedPlayer = PlayerLessRaw.substring(3);
			String Player = QuotedPlayer.replace("'", "");

			BlockTrackerSQL.blockTrack(Player, X, Y, Z, getTime(), BlockType);
		}
	}

	public String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String YMDHMS = dateFormat.format(date);
		return YMDHMS;

	}

}
