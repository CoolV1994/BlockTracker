
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
		return 0;
	}

	public String c(ae var1) {
		return "usage: bt usage";
	}

	//WIP
	//TODO
	public void a(ae CommandSender, String[] Arguments) {
		String Player = CommandSender.d_();
		if(BlockTrackerTool.isPlayerOnList(Player)){
			int ID = BlockTrackerTool.terms.lastIndexOf(Player);
			BlockTrackerTool.terms.remove(ID);
			CommandSender.a(new hy("BT disabled"));
		} else {
		BlockTrackerTool.terms.add(Player);
		CommandSender.a(new hy("BT enabled"));
		}
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}
}
