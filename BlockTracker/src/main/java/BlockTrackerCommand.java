import net.minecraft.server.MinecraftServer;

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
		CommandSender.a(new hy("§cUsage: Toggles the BlockTracker log tool."));
		CommandSender.a(new hy("§cUsage: Click a block with a log inhand or place a log."));
		return "§a/bt";
	}

	public void a(ae CommandSender, String[] Arguments) {
		String Player = CommandSender.d_();
		if(BlockTrackerTool.isPlayerTooled(Player)){
			int ID = BlockTrackerTool.TooledPlayers.lastIndexOf(Player);
			BlockTrackerTool.TooledPlayers.remove(ID);
			CommandSender.a(new hy("§2[BlockTracker] §cTool Disabled."));
		} else {
		BlockTrackerTool.TooledPlayers.add(Player);
		CommandSender.a(new hy("§2[BlockTracker] §aTool Enabled."));
		}
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}
}
