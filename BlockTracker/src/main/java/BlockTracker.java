
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
	public void BlockBreakEvent(aqu world, dt BRcoords, bec blocktype, ahd player) {
		if (Track) {
			String BlockType = String.valueOf(blocktype);

			//Converts the dt object to X, Y, and Z variables
			int X = BRcoords.n();
			int Y = BRcoords.o();
			int Z = BRcoords.p();
			
			//Isolates the playername from the ahd object
			String Player = player.d_();

			//Attempting to replace destroyed block with the exact same block
			//at the exact same coordiantes (in the same world)
			logger.info("setting block?");
			logger.info(world);
			logger.info(BRcoords);
			logger.info(blocktype);
			world.a(BRcoords, blocktype, 2);
			world.b(BRcoords, blocktype.c());
			//Insert to DB
			BlockTrackerSQL.insertBlockBreak(Player, X, Y, Z, getTime(), BlockType);
		}
	}

	public String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);

	}

	//Called when a player places a block
	//TODO WIP
	@SuppressWarnings(value = { "unused" })
	public void BlockPlaceEvent(aqu var1, dt BPcoords, bec var3, xm var4, amj var5) {
		if (Track == true) {
			String BlockType = String.valueOf(var3);

			//Converts the dt object to X, Y, and Z variables
			int X = BPcoords.n();
			int Y = BPcoords.o();
			int Z = BPcoords.p();

			//Isolates the playername from the ahd object
			//String Player = var4.d_();
			
			logger.info(X + Y + Z);
			logger.info(var1);
			logger.info(var3);
			logger.info(var4);
			logger.info(var5);

			//TODO
			//Make SQL function for inserting block placement
			//BlockTrackerSQL.insertBlockPlace(Player, X, Y, Z, getTime(), BlockType);
		}

	}

}

