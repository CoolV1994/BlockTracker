package RainbowBlockTracker;

import PluginReference.*;

import java.util.LinkedList;

public class Tool {
    public static LinkedList<String> TooledPlayers = new LinkedList<String>();
    public static final MC_ItemStack bedrock = MyPlugin.server.createItemStack(7, 1, 0);
    public static final MC_ItemStack wooden_pickaxe = MyPlugin.server.createItemStack(270, 1, 0);

	public static void getCoordEdits(MC_Player Player, MC_Location coords, MC_DirectionNESWUD dir, boolean placed) {
        int World = coords.dimension;
		int X = coords.getBlockX();
		int Y = coords.getBlockY();
		int Z = coords.getBlockZ();

        // Get the coordinates of where the missing block is.
        if (placed) {
            switch (dir) {
                case DOWN:
                    Y = Y - 1;
                    break;
                case UP:
                    Y = Y + 1;
                    break;
                case NORTH:
                    Z = Z - 1;
                    break;
                case SOUTH:
                    Z = Z + 1;
                    break;
                case EAST:
                    X = X + 1;
                    break;
                case WEST:
                    X = X - 1;
                    break;
            }
        }

		SQL.getBlockRecord(Player, World, X, Y, Z);
	}

	public static boolean isPlayerTooled(String Player) {
		if (!(TooledPlayers.isEmpty() || TooledPlayers.size() == 0)) {
			if (TooledPlayers.contains(Player)) {
				return true;
			}
		}
		return false;
	}

}
