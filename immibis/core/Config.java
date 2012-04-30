package immibis.core;

import forge.Configuration;
import forge.MinecraftForge;
import forge.Property;
import java.io.File;
import java.util.HashSet;
import net.minecraft.server.Block;
import net.minecraft.server.Item;

public class Config
{
    private static File configFile;
    private static Configuration config;
    private static boolean autoAssign;
    private static HashSet forceAllowReassign;

    public static boolean getBoolean(String var0, boolean var1)
    {
        boolean var2 = Boolean.valueOf(config.getOrCreateBooleanProperty(var0, "general", var1).value).booleanValue();
        config.save();
        return var2;
    }

    public static void save()
    {
        config.save();
    }

    public static int getBlockID(String var0, boolean var1)
    {
        try
        {
            return !var1 && !config.blockProperties.containsKey(var0 + ".id") ? 0 : Integer.parseInt(config.getOrCreateBlockIdProperty(var0 + ".id", 194).value);
        }
        catch (Exception var3)
        {
            MCVersionProxy.ML_ThrowException("This shouldn\'t be possible", var3);
            return 0;
        }
    }

    public static int getShiftedItemID(String var0, int var1)
    {
        Property var2 = config.getOrCreateIntProperty(var0, "block", var1);
        int var3 = Integer.valueOf(var2.value).intValue();
        int var4 = Block.byId.length;

        if (Item.byId[var3 - var4] != null || var3 == 0)
        {
            if (!autoAssign)
            {
                MinecraftForge.killMinecraft("immibis core", "Item " + var0 + " conflicts with " + Item.byId[var3 - var4] + " ");
            }

            var3 = -1;

            for (int var5 = Item.byId.length - 1; var5 >= 500; --var5)
            {
                if (Item.byId[var5] == null)
                {
                    var3 = var5 + var4;
                    break;
                }
            }

            if (var3 == -1)
            {
                MinecraftForge.killMinecraft("immibis core", "You\'re out of block IDs! Oh no! Remove some mods or install a block ID extender!");
            }

            var2.value = String.valueOf(var3);
            config.save();
        }

        return var3;
    }

    public static int getInt(String var0, int var1)
    {
        return Integer.valueOf(config.getOrCreateIntProperty(var0, "general", var1).value).intValue();
    }

    static
    {
        File var1 = new File(NonSharedProxy.getMinecraftDir(), "config");
        var1.mkdir();
        File var0 = new File(var1, "immibis.cfg");
        config = new Configuration(var0);
        config.load();
        autoAssign = getBoolean("autoAssign", true);
        ((Property)config.generalProperties.get("autoAssign")).value = "false";
        config.save();
        forceAllowReassign = new HashSet();
    }
}
