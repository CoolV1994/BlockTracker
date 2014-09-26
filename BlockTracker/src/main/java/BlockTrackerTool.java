import java.util.LinkedList;



//WIP
//TODO
public class BlockTrackerTool {
	
	static LinkedList<String> terms = new LinkedList<String>();
	
	public static boolean getPlayerBTTool(ahd Player, dt coords)
	{
		for(String str: terms) {
		    if(str.trim().contains(Player.d_())){
		    	getCoordEdits(Player, coords);
		        return true;
		    }
		}
		return false;
	}
	
	public static boolean getCoordEdits(ahd Player, dt coords)
	{
		Player.a(new hy("BlockBroken return SQL data."));
		return true;
	}

	public static boolean isPlayerOnList(String Player)
	{
		if(!(terms.isEmpty() || terms.size() == 0)){
			if(terms.contains(Player)){
		        return true;
			}
		    }
		return false;
	}
	
}
