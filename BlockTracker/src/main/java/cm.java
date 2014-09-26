import java.util.List;


//Example for setting blocks.
//Not apart of BlockTracker

public class cm extends z {

	public String c() {
		return "setblock";
	}

	public int a() {
		return 2;
	}

	public String c(ae var1) {
		return "commands.setblock.usage";
		// Syntax: /setblock x y z block data method datatag
	}

	public void a(ae sender, String[] arguments) throws dp, di {
		if(arguments.length < 4) {
			throw new dp("commands.setblock.usage", new Object[0]);
		} else {
			sender.a(ag.b, 0);
			dt coordinates = a(sender, arguments, 0, false); // Parameters 0 1 2 are the x y z coords
			atr block = z.g(sender, arguments[3]); // Param 3 is block name, z.g() parses name and returns atr
			int blockData = 0; // Param 4, if specified, is the block data value, defaults to 0
			if(arguments.length >= 5) {
				blockData = a(arguments[4], 0, 15);
			}

			aqu world = sender.e(); // Get world that sender is in
			if(!world.e(coordinates)) {
				throw new di("commands.setblock.outOfWorld", new Object[0]);
			} else {
				fn tile_NBT_data = new fn();
				boolean block_has_tile_entity = false;
				if(arguments.length >= 7 && block.x()) { // block.x() => block.hasTileEntity()
					String tile_JSON_data = a(sender, arguments, 6).c(); // Param 6 is user-supplied JSON data

					tile_NBT_data = gg.a(tile_JSON_data); // gg is JsonToNBT utility class
					block_has_tile_entity = true;
				}

				if(arguments.length >= 6) { // Param 5 is the method to use when setting the block
					if(arguments[5].equals("destroy")) {
						world.b(coordinates, true); // Destroy the target block, dropping resources
						if(block == aty.a) {
							// If we're setting to air, then it's already done. Return.
							a(sender, this, "commands.setblock.success", new Object[0]);
							return;
						}
					} else if(arguments[5].equals("keep") && !world.d(coordinates)) { // If block is not empty, throw exception
						throw new di("commands.setblock.noChange", new Object[0]);
					}
				}

				//Var13 seems to be block tile data
				bcm var13 = world.s(coordinates);
				if(var13 != null) {
					if(var13 instanceof vq) {
						((vq)var13).l();
					}

					world.a(coordinates, aty.a.P(), block == aty.a?2:4);
				}

				bec Block2Set = block.a(blockData);
				if(!world.a(coordinates, Block2Set, 2)) {
					throw new di("commands.setblock.noChange", new Object[0]);
				} else {
					if(block_has_tile_entity) {
						bcm var11 = world.s(coordinates);
						if(var11 != null) {
							tile_NBT_data.a("x", coordinates.n());
							tile_NBT_data.a("y", coordinates.o());
							tile_NBT_data.a("z", coordinates.p());
							var11.a(tile_NBT_data);
						}
					}

					world.b(coordinates, Block2Set.c());
					sender.a(ag.b, 1);
					a(sender, this, "commands.setblock.success", new Object[0]);
				}
			}
		}
	}

	public List<?> a(ae var1, String[] var2, dt var3) {
		return var2.length > 0 && var2.length <= 3?a(var2, 0, var3):(var2.length == 4?a(var2, atr.c.c()):(var2.length == 6?a(var2, new String[]{"replace", "destroy", "keep"}):null));
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
