
import net.minecraft.server.MinecraftServer;


//TODO WIP
public class BlockTrackerCommand extends z {

	public boolean a(ae var1) {
		return MinecraftServer.M().S() || super.a(var1);
	}

	public String c() {
		return "bt";
	}

	public int a() {
		return 4;
	}

	public String c(ae CommandSender) {
		return "/bt  --  Toggles LogBlock tool. Place a log (or break with a log inhand) to get edits on that position.";
	}

	//WIP
	//TODO
	public void a(ae CommandSender, String[] Arguments) {
		String Player = CommandSender.d_();
		if(BlockTrackerTool.isPlayerTooled(Player)){
			int ID = BlockTrackerTool.TooledPlayers.lastIndexOf(Player);
			BlockTrackerTool.TooledPlayers.remove(ID);
			CommandSender.a(new hy("BT disabled"));
		} else {
		BlockTrackerTool.TooledPlayers.add(Player);
		CommandSender.a(new hy("BT enabled"));
		}
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}
}
