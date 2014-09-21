import net.minecraft.server.MinecraftServer;

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

	public void a(ae var1, String[] var2) {
		String test = "test";
		var1.a(new hz("Response 1", test));
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}
}
