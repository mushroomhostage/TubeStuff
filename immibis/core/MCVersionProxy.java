package immibis.core;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;

public class MCVersionProxy
{
    public static void ML_ThrowException(String var0, Throwable var1)
    {
        ModLoader.throwException(var0, var1);
    }

    public static void ML_RegisterTileEntity(Class var0, String var1)
    {
        ModLoader.registerTileEntity(var0, var1);
    }

    public static void ML_RegisterBlock(Block var0)
    {
        ModLoader.registerBlock(var0);
    }

    public static void ML_RegisterBlock(Block var0, Class var1)
    {
        ModLoader.registerBlock(var0, var1);
    }

    public static void ML_AddRecipe(ItemStack var0, Object[] var1)
    {
        ModLoader.addRecipe(var0, var1);
    }

    public static void ML_AddShapelessRecipe(ItemStack var0, Object[] var1)
    {
        ModLoader.addShapelessRecipe(var0, var1);
    }

    public static int GetBlockIDCount()
    {
        return 256;
    }
}
