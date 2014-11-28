package RainbowBlockTracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import PluginReference.*;

public class MyPlugin extends PluginBase {
    public static String version = "1.4";
    public static MC_Server server = null;
	public static final Logger logger = Logger.getLogger("BlockTracker");
    public static ExecutorService queue;
	public static String host;
	public static String database;
	public static String dbuser;
	public static String dbpass;
    public static String dbtable;
    public static int queueThreads = 2;
    public static long queueShutdownTimeout = 60;
	public static boolean Track;

    public void onStartup(MC_Server argServer)
    {
        server = argServer;
        logger.info("RainbowBlockTracker v" + version + " starting up...");
        logger.info("Created by: Geistes. Ported and enhanced by: CoolV1994.");

        logger.info("Checking Config...");
        if (Config.readConfig()) {
            logger.info("Checking Database...");
            if (SQL.checkDB()) {
                logger.info("Checking Table...");
                if (SQL.checkTable()) {
                    queue = Executors.newFixedThreadPool(queueThreads);
                    Track = true;
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

        server.registerCommand(new BTCommand());
        server.registerCommand(new BTLookupCommand());
    }

    public PluginInfo getPluginInfo()
    {
        PluginInfo info = new PluginInfo();
        info.description = "Block logging mod for Rainbow servers.";
        return info;
    }

	//Called when a player breaks a block
    public void onBlockBroke(final MC_Player plr, final MC_Location loc, final MC_Block blk) {
		if (Track) {
            queue.execute(new Runnable() {
                public void run() {
                    String Time = getTime();
                    String Player = plr.getName();

                    int World = loc.dimension;
                    int X = loc.getBlockX();
                    int Y = loc.getBlockY();
                    int Z = loc.getBlockZ();

                    String Block = BlockHelper.getBlockFriendlyName(blk.getId(), blk.getSubtype());
                    String Event = "Broke";

                    SQL.insertBlockEvent(Player, World, X, Y, Z, Time, Block, Event);
                }
            });
		}
	}

	//Called when a player places a block
	public void onItemPlaced(final MC_Player plr, final MC_Location loc, final MC_ItemStack isHandItem, MC_Location locPlacedAgainst, MC_DirectionNESWUD dir) {
		if (Track) {
            queue.execute(new Runnable() {
                public void run() {
                    String Time = getTime();
                    String Player = plr.getName();

                    int World = loc.dimension;
                    int X = loc.getBlockX();
                    int Y = loc.getBlockY();
                    int Z = loc.getBlockZ();

                    String Block = isHandItem.getFriendlyName();
                    String Event = "Placed";

                    SQL.insertBlockEvent(Player, World, X, Y, Z, Time, Block, Event);
                }
            });
		}
	}

    public void onAttemptBlockBreak(final MC_Player plr, final MC_Location loc, MC_EventInfo ei) {
        if (Tool.TooledPlayers.contains(plr.getName()) && (plr.getItemInHand().getId() == 7 || plr.getItemInHand().getId() == 270)) {
            ei.isCancelled = true;
            queue.execute(new Runnable() {
                public void run() {
                    Tool.getCoordEdits(plr, loc, MC_DirectionNESWUD.UNSPECIFIED, false);
                }
            });
        }
    }

    public void onAttemptPlaceOrInteract(final MC_Player plr, final MC_Location loc, MC_EventInfo ei, final MC_DirectionNESWUD dir) {
        if (Tool.TooledPlayers.contains(plr.getName()) && (plr.getItemInHand().getId() == 7 || plr.getItemInHand().getId() == 270)) {
            ei.isCancelled = true;
            queue.execute(new Runnable() {
                public void run() {
                    Tool.getCoordEdits(plr, loc, dir, true);
                }
            });
        }
    }
	
	public String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

    public void onShutdown() {
        logger.info("Shutting down BlockTracker...");
        queue.shutdown();
        try {
            queue.awaitTermination(queueShutdownTimeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.warning("Error waiting for unsent block logs.");
        }
        logger.info("RainbowBlockTracker Shutdown.");
    }
}

