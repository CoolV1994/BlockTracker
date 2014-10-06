import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockTracker extends Thread {
	public static final Logger logger = LogManager.getLogger();
    public static final ExecutorService queue = Executors.newFixedThreadPool(2);
	public static String host;
	public static String database;
	public static String dbuser;
	public static String dbpass;
	public static boolean Track;

	public synchronized void run() {
		this.setName("BlockTracker");
		logger.info("BlockTracker 1.0");
		logger.info("Server: v1.8");
		logger.info("-Gesites <3-");
		logger.info("Checking Config...");
		if (BlockTrackerConfig.readConfig()) {
			logger.info("Checking SQL DB...");
			if (BlockTrackerSQL.checkDB()) {
				logger.info("Checking SQL Table...");
				if (BlockTrackerSQL.checkTable()) {
					logger.info("Everything appears OK");
					logger.info("Enabled!");
					Track = true;
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

	//Called when a player breaks a block
	public void BlockBreakEvent(aqu world, dt BBcoords, bec blocktype, ahd player) {
		if (Track) {
			final String BlockType = String.valueOf(blocktype);

			//Converts the dt object to X, Y, and Z variables
			final int X = BBcoords.n();
			final int Y = BBcoords.o();
			final int Z = BBcoords.p();
			
			//Isolates the playername from the ahd object
			final String Player = player.d_();

			//Insert to DB
            queue.execute(new Runnable() {
                public void run() {
                    BlockTrackerSQL.insertBlockBreak(Player, X, Y, Z, getTime(), BlockType);
                }
            });
		}
	}

	//Called when a player places a block
	public void BlockPlaceEvent(aqu world, dt BPcoords, bec blocktype, xm entitylivingbase, amj itemstack) {
		if (Track) {
			final String BlockType = String.valueOf(blocktype);

			//Converts the dt object to X, Y, and Z variables
			final int X = BPcoords.n();
			final int Y = BPcoords.o();
			final int Z = BPcoords.p();
			
			//Isolates the playername from the xm object
			final String Player = entitylivingbase.d_();

            queue.execute(new Runnable() {
                public void run() {
                    BlockTrackerSQL.insertBlockPlace(Player, X, Y, Z, getTime(), BlockType);
                }
            });
		}

	}
	
	public String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}

