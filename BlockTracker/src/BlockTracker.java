import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//////////
//Geistes's BlockTracker
//Minecraft 1.8 Vanilla
//Under MIT License
//////////

public class BlockTracker extends Thread{

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
		logger.info("Checking Config");
		if(BlockTrackerConfig.readConfig() == true){
			logger.info("Checking SQL DB");
			if(BlockTrackerSQL.checkDB() == true){
				logger.info("Checking SQL Table");
				if(BlockTrackerSQL.checkTable() == true){
					
				} else { Track = false; }
			} else { Track = false; }
		} else { Track = false; }
logger.info("Exiting.");
	}

	public void BlockBreakEvent(aqu var1, dt var2, bec var3, ahd var4) {
		//if(Track==true){
		logger.warn("BlockBreakEvent");
		logger.info("AQU: " + var1);
		logger.info("DT: " + var2);
		logger.info("BEC: " + var3);
		logger.info("AHD: " + var4);
		logger.info(host + database + dbuser + dbpass + Track);
		//}
	}

}
