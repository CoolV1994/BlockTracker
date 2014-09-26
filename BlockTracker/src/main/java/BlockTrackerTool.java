import java.util.LinkedList;



//WIP
//TODO
public class BlockTrackerTool {
	
	static LinkedList<String> TooledPlayers = new LinkedList<String>();
	
	public static void getCoordEdits(ahd Player, dt coords)
	{
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
