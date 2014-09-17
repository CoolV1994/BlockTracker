import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//////////
//Geistes's BlockTracker
//Minecraft 1.8 Vanilla
//Under MIT License
//////////

public class BlockTracker extends Thread{

	public static final Logger logger = LogManager.getLogger();
	static BlockTrackerSQL blocktracksql = new BlockTrackerSQL();
	public static int[] Blocks;
	public static boolean Track;
	
	public void run() {

		logger.info("Checking Config");
		if(BlockTrackerConfig.readConfig() == true){
			logger.info("Checking SQL DB");
			if(BlockTrackerSQL.checkDB() == true){
				
			} else { Track = false; }
		} else { Track = false; }

	}

	public static void BlockBreakEvent(aqu var1, dt var2, bec var3, ahd var4) {
		if(Track==true){
		logger.warn("BlockBreakEvent");
		logger.info("AQU: " + var1);
		logger.info("DT: " + var2);
		logger.info("BEC: " + var3);
		logger.info("AHD: " + var4);
		}
	}

}
