package immibis.core.covers.recipes;

import immibis.core.BlockMetaPair;
import immibis.core.covers.CoverSystemProxy;
import java.util.HashMap;
import net.minecraft.server.CraftingRecipe;
import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.ItemStack;

public class RecipeVerticalCut implements CraftingRecipe
{
    private static HashMap damageMap = new HashMap();

    public static void addMap(BlockMetaPair var0, ItemStack var1)
    {
        damageMap.put(var0, var1);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean a(InventoryCrafting var1)
    {
        return this.b(var1) != null;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack b(InventoryCrafting var1)
    {
        for (int var3 = 0; var3 <= 2; ++var3)
        {
            for (int var4 = 0; var4 <= 1; ++var4)
            {
                ItemStack var2 = this.check(var1, var3, var4);

                if (var2 != null)
                {
                    return var2;
                }
            }
        }

        return null;
    }

    private ItemStack check(InventoryCrafting var1, int var2, int var3)
    {
        ItemStack var4 = var1.b(var2, var3);

        if (var4 != null && var4.id == CoverSystemProxy.itemSaw.id)
        {
            ItemStack var5 = var1.b(var2, var3 + 1);
            return var5 == null ? null : ItemStack.b((ItemStack)damageMap.get(new BlockMetaPair(var5.id, var5.getData())));
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the size of the recipe area
     */
    public int a()
    {
        return 2;
    }

    public ItemStack b()
    {
        return new ItemStack(CoverSystemProxy.blockMultipart, 1, 0);
    }
}
