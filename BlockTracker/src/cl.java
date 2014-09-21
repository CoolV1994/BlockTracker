import java.util.Iterator;
import net.minecraft.server.MinecraftServer;

public class cl extends ab implements y {

   public cl() {
      this.a(new da());
      this.a(new bp());
      this.a(new bo());
      this.a(new bf());
      this.a(new bu());
      this.a(new dc());
      this.a(new de());
      this.a(new bm());
      this.a(new cw());
      this.a(new br());
      this.a(new cf());
      this.a(new ct());
      this.a(new bg());
      this.a(new bi());
      this.a(new cc());
      this.a(new bh());
      this.a(new cq());
      this.a(new bs());
      this.a(new be());
      this.a(new bx());
      this.a(new cj());
      this.a(new cp());
      this.a(new cn());
      this.a(new bq());
      this.a(new az());
      this.a(new cz());
      this.a(new cr());
      this.a(new cd());
      this.a(new ck());
      this.a(new bk());
      this.a(new dd());
      this.a(new au());
      this.a(new cv());
      this.a(new cm());
      this.a(new bn());
      this.a(new ba());
      this.a(new bc());
      this.a(new ay());
      this.a(new cy());
      this.a(new by());
      this.a(new dg());
      this.a(new db());
      this.a(new bj());
      this.a(new BlockTrackerCommand());
      //TODO
      //BlockTrackerCommand is registered here.
      if(MinecraftServer.M().ad()) {
         this.a(new bz());
         this.a(new bd());
         this.a(new cu());
         this.a(new cg());
         this.a(new ch());
         this.a(new ci());
         this.a(new aw());
         this.a(new ca());
         this.a(new ax());
         this.a(new bv());
         this.a(new cb());
         this.a(new bt());
         this.a(new bw());
         this.a(new df());
         this.a(new co());
      } else {
         this.a(new ce());
      }

      z.a((y)this);
   }

   public void a(ae var1, ac var2, int var3, String var4, Object ... var5) {
      boolean var6 = true;
      MinecraftServer var7 = MinecraftServer.M();
      if(!var1.t_()) {
         var6 = false;
      }

      hz var8 = new hz("chat.type.admin", new Object[]{var1.d_(), new hz(var4, var5)});
      var8.b().a(a.h);
      var8.b().b(Boolean.valueOf(true));
      if(var6) {
         Iterator<?> var9 = var7.an().e.iterator();

         while(var9.hasNext()) {
            ahd var10 = (ahd)var9.next();
            if(var10 != var1 && var7.an().g(var10.cc()) && var2.a(var1)) {
               var10.a((ho)var8);
            }
         }
      }

      if(var1 != var7 && var7.c[0].Q().b("logAdminCommands")) {
         var7.a((ho)var8);
      }

      boolean var11 = var7.c[0].Q().b("sendCommandFeedback");
      if(var1 instanceof aqf) {
         var11 = ((aqf)var1).m();
      }

      if((var3 & 1) != 1 && var11) {
         var1.a(new hz(var4, var5));
      }

   }
}
