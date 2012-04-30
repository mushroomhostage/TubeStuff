package immibis.tubestuff;

import immibis.core.MCVersionProxy;
import immibis.core.config.ExtendedForgeConfigReader;
import java.io.IOException;
import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.mod_TubeStuff;

public class SharedProxy
{
    public static boolean enableBHCParticles;
    public static boolean enableBHC;
    public static boolean enableBHCAnim;

    static boolean CC_CheckArgs(Object[] var0, Class[] var1)
    {
        if (var0.length != var1.length)
        {
            return false;
        }
        else
        {
            for (int var2 = 0; var2 < var0.length; ++var2)
            {
                if (var0[var2].getClass() != var1[var2])
                {
                    return false;
                }
            }

            return true;
        }
    }

    private static Class findClass(String var0)
    {
        try
        {
            return SharedProxy.class.getClassLoader().loadClass("net.minecraft.server." + var0);
        }
        catch (ClassNotFoundException var4)
        {
            try
            {
                return SharedProxy.class.getClassLoader().loadClass(var0);
            }
            catch (ClassNotFoundException var3)
            {
                return null;
            }
        }
    }

    public static void ModsLoaded()
    {
        boolean var0 = true;
        int var1 = -1;
        int var2 = -1;
        boolean var3 = true;
        int var4 = -1;
        int var16;
        int var15;

        try
        {
            ExtendedForgeConfigReader var5 = new ExtendedForgeConfigReader("redpower/redpower.cfg");

            try
            {
                var15 = Integer.parseInt(var5.getConfigEntry("blocks.machine.machine.id"));

                if (!Block.byId[var15].getClass().getName().equals("eloraam.machine.BlockMachine"))
                {
                    var15 = -1;
                }
            }
            catch (Exception var13)
            {
                System.out.println(mod_TubeStuff.class.getSimpleName() + ": RP2 Machine doesn\'t seem to be installed");
                var15 = -1;
            }

            try
            {
                var16 = Integer.parseInt(var5.getConfigEntry("blocks.logic.logic.id"));

                if (!Block.byId[var16].getClass().getName().equals("eloraam.logic.BlockLogic"))
                {
                    var16 = -1;
                }
            }
            catch (Exception var12)
            {
                System.out.println(mod_TubeStuff.class.getSimpleName() + ": RP2 Logic doesn\'t seem to be installed");
                var16 = -1;
            }
        }
        catch (IOException var14)
        {
            System.out.println(mod_TubeStuff.class.getSimpleName() + ": RP2 doesn\'t seem to be installed");
            var16 = -1;
            var15 = -1;
        }

        ClassLoader var17 = SharedProxy.class.getClassLoader();
        Class var6;

        try
        {
            var6 = findClass("BuildCraftTransport");

            if (var6 != null)
            {
                var1 = ((Item)var6.getDeclaredField("pipeItemsWood").get((Object)null)).id;
            }
        }
        catch (Exception var11)
        {
            System.out.println(mod_TubeStuff.class.getSimpleName() + ": BC Transport doesn\'t seem to be installed");
        }

        try
        {
            var6 = findClass("BuildCraftEnergy");

            if (var6 != null)
            {
                var2 = ((Block)var6.getDeclaredField("engineBlock").get((Object)null)).id;
            }
        }
        catch (Exception var10)
        {
            System.out.println(mod_TubeStuff.class.getSimpleName() + ": BC Energy doesn\'t seem to be installed");
        }

        try
        {
            var6 = findClass("BuildCraftFactory");

            if (var6 != null)
            {
                var4 = ((Block)var6.getDeclaredField("autoWorkbenchBlock").get((Object)null)).id;
            }
        }
        catch (Exception var9)
        {
            System.out.println(mod_TubeStuff.class.getSimpleName() + ": BC Factory doesn\'t seem to be installed");
        }

        ItemStack var18 = new ItemStack(mod_TubeStuff.block, 1, 0);
        ItemStack var7 = new ItemStack(mod_TubeStuff.block, 1, 1);
        new ItemStack(mod_TubeStuff.block, 1, 2);

        if (var15 != -1)
        {
            if (var16 != -1)
            {
                MCVersionProxy.ML_AddShapelessRecipe(var18, new Object[] {new ItemStack(var15, 1, 3), Block.CHEST, new ItemStack(var16, 1, 0)});
            }
            else
            {
                MCVersionProxy.ML_AddShapelessRecipe(var18, new Object[] {new ItemStack(var15, 1, 3), Block.CHEST});
            }

            MCVersionProxy.ML_AddRecipe(var7, new Object[] {"GFG", "WCW", "WcW", 'F', new ItemStack(var15, 1, 3), 'G', Item.GOLD_INGOT, 'C', Block.WORKBENCH, 'c', Block.CHEST, 'W', Block.WOOD});
        }
        else
        {
            MCVersionProxy.ML_AddRecipe(var7, new Object[] {"GGG", "WCW", "WcW", 'G', Item.GOLD_INGOT, 'C', Block.WORKBENCH, 'c', Block.CHEST, 'W', Block.WOOD});
        }

        if (var1 != -1)
        {
            if (var2 != -1)
            {
                MCVersionProxy.ML_AddShapelessRecipe(var18, new Object[] {Item.byId[var1], Block.CHEST, new ItemStack(var2, 1, 0)});
            }
            else
            {
                MCVersionProxy.ML_AddShapelessRecipe(var18, new Object[] {Item.byId[var1], Block.CHEST});
            }

            MCVersionProxy.ML_AddRecipe(var7, new Object[] {"PWP", "WCW", "PcP", 'G', Item.GOLD_INGOT, 'C', var4 == -1 ? Block.WORKBENCH : Block.byId[var4], 'c', Block.CHEST, 'W', Item.byId[var1], 'P', Block.WOOD});
        }
    }
}
