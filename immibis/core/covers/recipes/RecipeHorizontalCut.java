package immibis.core.covers.recipes;

import immibis.core.BlockMetaPair;
import immibis.core.covers.CoverSystemProxy;
import java.util.HashMap;
import net.minecraft.src.CraftingRecipe;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;

public class RecipeHorizontalCut implements CraftingRecipe
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
        for (int var3 = 0; var3 <= 1; ++var3)
        {
            for (int var4 = 0; var4 <= 2; ++var4)
            {
                ItemStack var2 = this.check(var1, var3, var4, true);

                if (var2 != null)
                {
                    return var2;
                }

                var2 = this.check(var1, var3, var4, false);

                if (var2 != null)
                {
                    return var2;
                }
            }
        }

        return null;
    }

    private ItemStack check(InventoryCrafting var1, int var2, int var3, boolean var4)
    {
        ItemStack var5 = var1.b(var2 + (var4 ? 1 : 0), var3);

        if (var5 != null && var5.id == CoverSystemProxy.itemSaw.id)
        {
            ItemStack var6 = var1.b(var2 + (var4 ? 0 : 1), var3);
            return var6 == null ? null : ItemStack.b((ItemStack)damageMap.get(new BlockMetaPair(var6.id, var6.getData())));
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
