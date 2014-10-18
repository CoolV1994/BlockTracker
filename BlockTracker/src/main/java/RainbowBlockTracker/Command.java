package RainbowBlockTracker;

import PluginReference.*;

import java.util.Arrays;
import java.util.List;

public class Command implements MC_Command {
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
    public void handleCommand(MC_Player plr, String[] strings) {
        String Player = plr.getName();

        if (strings.length == 1) {
            if (strings[0].equals("pick")) {
                plr.setItemInHand(Tool.wooden_pickaxe);
                plr.sendMessage(ChatColor.GREEN + "You receive BlockTracker Tool (Wooden Pickaxe).");
            }
            if (strings[0].equals("block")) {
                plr.setItemInHand(Tool.bedrock);
                plr.sendMessage(ChatColor.GREEN + "You receive BlockTracker Tool (Bedrock).");
            }
        } else {
            if (Tool.isPlayerTooled(Player)) {
                Tool.TooledPlayers.remove(Player);
                plr.sendMessage(ChatColor.RED + "[BlockTracker] Tool Disabled.");
            } else {
                Tool.TooledPlayers.add(Player);
                plr.sendMessage(ChatColor.GREEN + "[BlockTracker] Tool Enabled.");
                plr.setItemInHand(Tool.wooden_pickaxe);
                plr.sendMessage(ChatColor.GREEN + "You receive BlockTracker Tool (Wooden Pickaxe).");
            }
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
