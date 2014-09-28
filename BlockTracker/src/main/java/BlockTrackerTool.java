import java.util.LinkedList;

public class BlockTrackerTool {
	
	static LinkedList<String> TooledPlayers = new LinkedList<String>();
	
	public static void getCoordEdits(ahd Player, dt coords)
	{
		//TODO
		//Fetch all SQL edits @ DT coords
		//Order them in a list or some shit like that
		Player.a(new hy("BlockBroken return SQL data."));
	}

	public static boolean isPlayerTooled(String Player)
	{
		if(!(TooledPlayers.isEmpty() || TooledPlayers.size() == 0)){
			if(TooledPlayers.contains(Player)){
		        return true;
			}
		    }
		return false;
	}
	
	
}
