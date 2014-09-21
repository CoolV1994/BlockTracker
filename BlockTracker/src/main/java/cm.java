import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Example for setting blocks.
//Not apart of BlockTracker

public class cm extends z {
	
	public static final Logger logger = LogManager.getLogger();

   public String c() {
      return "setblock";
   }

   public int a() {
      return 2;
   }

   public String c(ae var1) {
      return "commands.setblock.usage";
   }

   public void a(ae sender, String[] arguments) {
      if(arguments.length < 4) {
         try {
			throw new dp("commands.setblock.usage", new Object[0]);
		} catch (dp e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      } else {
         sender.a(ag.b, 0);
         dt coordinates = a(sender, arguments, 0, false);
         logger.info("dt var3 27: ", coordinates);
         atr block = z.g(sender, arguments[3]);
         logger.info("atr var4 29: ", block);
         int SomeSpecificArgument = 0;
         if(arguments.length >= 5) {
            SomeSpecificArgument = a(arguments[4], 0, 15);
         }

         aqu world = sender.e();
         logger.info("aqu var6 36: ", world);
         if(!world.e(coordinates)) {
            try {
				throw new di("commands.setblock.outOfWorld", new Object[0]);
			} catch (di e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         } else {
            fn var7 = new fn();
            boolean var8 = false;
            if(arguments.length >= 7 && block.x()) {
               String var9 = a(sender, arguments, 6).c();

               var7 = gg.a(var9);
			  var8 = true;
            }

            if(arguments.length >= 6) {
               if(arguments[5].equals("destroy")) {
                  world.b(coordinates, true);
                  if(block == aty.a) {
                     a(sender, this, "commands.setblock.success", new Object[0]);
                     return;
                  }
               } else if(arguments[5].equals("keep") && !world.d(coordinates)) {
                  try {
					throw new di("commands.setblock.noChange", new Object[0]);
				} catch (di e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               }
            }

            //Var13 is the location in the world?
            bcm var13 = world.s(coordinates);
            if(var13 != null) {
               if(var13 instanceof vq) {
                  ((vq)var13).l();
               }

               world.a(coordinates, aty.a.P(), block == aty.a?2:4);
            }

            bec Block2Set = block.a(SomeSpecificArgument);
            logger.info("aqu: ", String.valueOf(world));
            logger.info("dt: ", String.valueOf(coordinates));
            logger.info("bec: ", String.valueOf(Block2Set));
            if(!world.a(coordinates, Block2Set, 2)) {
               try {
				throw new di("commands.setblock.noChange", new Object[0]);
			} catch (di e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            } else {
               if(var8) {
                  bcm var11 = world.s(coordinates);
                  if(var11 != null) {
                     var7.a("x", coordinates.n());
                     var7.a("y", coordinates.o());
                     var7.a("z", coordinates.p());
                     var11.a(var7);
                  }
               }

               world.b(coordinates, Block2Set.c());
               sender.a(ag.b, 1);
               logger.info("var6: ", world);
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
