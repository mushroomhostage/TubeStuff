package immibis.core.covers.recipes;

import immibis.core.covers.CoverSystemProxy;
import java.util.HashMap;
import net.minecraft.src.CraftingRecipe;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;

public class RecipeHollowCover implements CraftingRecipe
{
    private static HashMap damageMap = new HashMap();

    public static void addMap(int var0, int var1)
    {
        damageMap.put(Integer.valueOf(var0), Integer.valueOf(var1));
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
        if (var1.getSize() != 9)
        {
            return null;
        }
        else
        {
            boolean var2 = true;
            int var3 = -1;

            for (int var4 = 0; var4 < 9; ++var4)
            {
                ItemStack var5 = var1.getItem(var4);

                if (var4 == 4 && var5 != null)
                {
                    return null;
                }

                if (var5 == null)
                {
                    return null;
                }

                if (var5.id != CoverSystemProxy.blockMultipart.id)
                {
                    return null;
                }

                if (var3 == -1)
                {
                    var3 = var5.getData();
                }
                else if (var3 != var5.getData())
                {
                    return null;
                }
            }

            Integer var6 = (Integer)damageMap.get(Integer.valueOf(var3));

            if (var6 == null)
            {
                return null;
            }
            else
            {
                return new ItemStack(CoverSystemProxy.blockMultipart.id, 8, var6.intValue());
            }
        }
    }

    /**
     * Returns the size of the recipe area
     */
    public int a()
    {
        return 3;
    }

    public ItemStack b()
    {
        return new ItemStack(CoverSystemProxy.blockMultipart, 1, 0);
    }
}
