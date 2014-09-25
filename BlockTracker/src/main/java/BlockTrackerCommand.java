
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

	public void a(ae CommandSender, String[] Arguments) {
		CommandSender.a(new hy("Response 1"));
		aqu world = CommandSender.e();
		dt BBcoords = CommandSender.c();
		world.a(BBcoords, atr.d(7), 3);
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}
}
