package RainbowBlockTracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import PluginReference.*;

public class MyPlugin extends PluginBase {
    public static String version = "1.1";
    public static MC_Server server = null;
	public static final Logger logger = Logger.getLogger("BlockTracker");
    public static final ExecutorService queue = Executors.newFixedThreadPool(2);
	public static String host;
	public static String database;
	public static String dbuser;
	public static String dbpass;
	public static boolean Track;

    public void onStartup(MC_Server argServer)
    {
        server = argServer;
        logger.info("RainbowBlockTracker v" + version + " starting up...");
        logger.info("Created by: Geistes. Ported by: CoolV1994.");

        logger.info("Checking Config...");
        if (Config.readConfig()) {
            logger.info("Checking Database...");
            if (SQL.checkDB()) {
                logger.info("Checking Table...");
                if (SQL.checkTable()) {
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

        server.registerCommand(new Command());
    }

    public PluginInfo getPluginInfo()
    {
        PluginInfo info = new PluginInfo();
        info.description = "Block logging mod for Rainbow servers.";
        return info;
    }

	//Called when a player breaks a block
    public void onBlockBroke(MC_Player plr, MC_Location loc, MC_Block blk) {
		if (Track) {
            final String Time = getTime();
            final String Player = plr.getName();

            final int World = loc.dimension;
            final int X = loc.getBlockX();
            final int Y = loc.getBlockY();
            final int Z = loc.getBlockZ();

            final String Block = BlockHelper.getBlockFriendlyName(blk.getId(), blk.getSubtype());
            final String Event = "Broke";

			//Insert to DB
            queue.execute(new Runnable() {
                public void run() {
                    SQL.insertBlockEvent(Player, World, X, Y, Z, Time, Block, Event);
                }
            });
		}
	}

	//Called when a player places a block
	public void onItemPlaced(MC_Player plr, MC_Location loc, MC_ItemStack isHandItem, MC_Location locPlacedAgainst, MC_DirectionNESWUD dir) {
		if (Track) {
            final String Time = getTime();
            final String Player = plr.getName();

            final int World = loc.dimension;
			final int X = loc.getBlockX();
			final int Y = loc.getBlockY();
			final int Z = loc.getBlockZ();

            final String Block = isHandItem.getFriendlyName();
            final String Event = "Placed";

            queue.execute(new Runnable() {
                public void run() {
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
}

