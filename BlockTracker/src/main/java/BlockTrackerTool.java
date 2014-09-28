import java.util.LinkedList;

public class BlockTrackerTool {

	static LinkedList<String> TooledPlayers = new LinkedList<String>();

	public static void getCoordEdits(ahd Player, dt coords, String event) {

		int X = coords.n();
		int Y = coords.o();
		int Z = coords.p();

		BlockTrackerSQL.getBlockRecord(X, Y, Z, event);
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
