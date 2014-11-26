package RainbowBlockTracker;

import PluginReference.*;

import java.util.Arrays;
import java.util.List;

public class BTCommand implements MC_Command {
    @Override
    public String getCommandName() {
        return "bt";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("blocktracker");
    }

    @Override
    public String getHelpLine(MC_Player mc_player) {
        return ChatColor.AQUA + "/bt" + ChatColor.GOLD + " [pick | block]" + ChatColor.WHITE + " - Toggle BlockTracker Tool.";
    }

    @Override
    public void handleCommand(MC_Player plr, String[] args) {
        String Player = plr.getName();

        if (args.length == 0) {
            if (Tool.isPlayerTooled(Player)) {
                Tool.TooledPlayers.remove(Player);
                plr.sendMessage(ChatColor.RED + "[BlockTracker] Tool Disabled.");
                return;
            } else {
                Tool.TooledPlayers.add(Player);
                plr.sendMessage(ChatColor.GREEN + "[BlockTracker] Tool Enabled.");
                return;
            }
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("pick")) {
                String oldItem = plr.getItemInHand().getFriendlyName();
                plr.setItemInHand(Tool.wooden_pickaxe);
                plr.sendMessage(ChatColor.GREEN + "Replaced " + oldItem + " with the BlockTracker Tool (Wooden Pickaxe).");
                return;
            }
            if (args[0].equalsIgnoreCase("block")) {
                String oldItem = plr.getItemInHand().getFriendlyName();
                plr.setItemInHand(Tool.bedrock);
                plr.sendMessage(ChatColor.GREEN + "Replaced " + oldItem + " with the BlockTracker Tool (Bedrock).");
                return;
            }
        }
        if (args.length > 0) {
            getHelpLine(plr);
        }
    }

    @Override
    public boolean hasPermissionToUse(MC_Player plr) {
        return plr.hasPermission("blocktracker.tool");
    }

    @Override
    public List<String> getTabCompletionList(MC_Player mc_player, String[] strings) {
        return null;
    }
}
