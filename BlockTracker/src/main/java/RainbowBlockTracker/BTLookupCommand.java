package RainbowBlockTracker;

import PluginReference.ChatColor;
import PluginReference.MC_Command;
import PluginReference.MC_Player;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Vinnie on 11/12/2014.
 */
public class BTLookupCommand implements MC_Command {
    @Override
    public String getCommandName() {
        return "btlookup";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("lookup");
    }

    @Override
    public String getHelpLine(MC_Player mc_player) {
        return ChatColor.RED + "/btlookup [ player | coord | recent ]";
    }

    @Override
    public void handleCommand(MC_Player plr, String[] args) {
        if (args.length == 0) {
            // Help
            plr.sendMessage(ChatColor.RED + "Use /btlookup [ player | coord | recent ]");
            return;
        }
        if (args[0].equalsIgnoreCase("player")) {
            if (args.length == 2) {
                SQL.lookupPlayer(plr, args[1], 0);
                return;
            }
            if (args.length == 3) {
                SQL.lookupPlayer(plr, args[1], Integer.parseInt(args[2]));
                return;
            }
            // Help
            plr.sendMessage(ChatColor.RED + "Use /bt lookup player (username) [page]");
            return;
        }
        if (args[0].equalsIgnoreCase("coord")) {
            // X, Y, Z
            if (args.length == 4) {
                SQL.lookupCoord(plr, plr.getLocation().dimension, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), 0, 0);
                return;
            }
            // X, Y, Z, Page
            if (args.length == 5) {
                SQL.lookupCoord(plr, plr.getLocation().dimension, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), 0,Integer.parseInt(args[4]));
                return;
            }
            // X, Y, Z, Page, Radius
            if (args.length == 6) {
                SQL.lookupCoord(plr, plr.getLocation().dimension, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[5]), Integer.parseInt(args[4]));
                return;
            }
            // X, Y, Z, Page, Radius, World
            if (args.length == 7) {
                SQL.lookupCoord(plr, Integer.parseInt(args[6]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[5]), Integer.parseInt(args[4]));
                return;
            }
            // Help
            plr.sendMessage(ChatColor.RED + "Use /bt lookup coord (x) (y) (z) [page] [radius] [world]");
            return;
        }
        if (args[0].equalsIgnoreCase("recent")) {
            if (args.length == 1) {
                SQL.lookupRecent(plr, 0);
                return;
            }
            if (args.length == 2) {
                SQL.lookupRecent(plr, Integer.parseInt(args[1]));
                return;
            }
            // Help
            plr.sendMessage(ChatColor.RED + "Use /bt lookup recent [page]");
            return;
        }
        // Help
        plr.sendMessage(ChatColor.RED + "Use /btlookup [ player | coord | recent ]");
    }

    @Override
    public boolean hasPermissionToUse(MC_Player mc_player) {
        return mc_player.hasPermission("blocktracker.lookup");
    }

    @Override
    public List<String> getTabCompletionList(MC_Player mc_player, String[] strings) {
        return null;
    }
}
