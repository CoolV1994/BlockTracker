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
		ahd ahdPlayer = (ahd)CommandSender;
		String Player = CommandSender.d_();
		if(BlockTrackerTool.isPlayerTooled(Player)){
			int ID = BlockTrackerTool.TooledPlayers.lastIndexOf(Player);
			BlockTrackerTool.TooledPlayers.remove(ID);
			CommandSender.a(new hy("§2[BlockTracker] §cTool Disabled."));
		} else {
		BlockTrackerTool.TooledPlayers.add(Player);
		CommandSender.a(new hy("§2[BlockTracker] §aTool Enabled."));
		
		//Give the player 2 log blocks.
		qw EntityPlayer = a(CommandSender, CommandSender.d_());
		alq Item = f(CommandSender, "log");
		amj ItemStack = new amj(Item, 2, 0);
		adw EntityItem = EntityPlayer.a(ItemStack, false);
		Boolean Robolo = ahdPlayer.bg.a(ItemStack); //Put it in their inventory
		if(!Robolo){ //if that fails drop it on them.
			EntityItem.q(); //Set position to players position
			EntityItem.b(CommandSender.d_()); //Drop it on them.
		}
		}
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}
}
