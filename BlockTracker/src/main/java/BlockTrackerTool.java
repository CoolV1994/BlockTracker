import java.util.ArrayList;
import java.util.List;



//WIP
//TODO
public class BlockTrackerTool {
	
	static List<String> terms = new ArrayList<String>();
	
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
		for(String str: terms) {
		    if(str.trim().contains(Player)){
		        return true;
		    }
		}
		return false;
	}
	
}
