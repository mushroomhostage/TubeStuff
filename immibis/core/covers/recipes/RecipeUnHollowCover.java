package immibis.core.covers.recipes;

import immibis.core.covers.CoverSystemProxy;
import java.util.HashMap;
import net.minecraft.server.CraftingRecipe;
import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.ItemStack;

import org.bukkit.inventory.Recipe;

public class RecipeUnHollowCover implements CraftingRecipe
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
        int var2 = -1;

        for (int var3 = 0; var3 < var1.getSize(); ++var3)
        {
            ItemStack var4 = var1.getItem(var3);

            if (var4 != null && var4.id == CoverSystemProxy.blockMultipart.id)
            {
                if (var2 != -1)
                {
                    return null;
                }

                var2 = var3;
            }
        }

        if (var2 == -1)
        {
            return null;
        }
        else
        {
            ItemStack var5 = var1.getItem(var2);
            Integer var6 = (Integer)damageMap.get(Integer.valueOf(var5.getData()));

            if (var6 == null)
            {
                return null;
            }
            else
            {
                return new ItemStack(var5.id, 1, var6.intValue());
            }
        }
    }

    /**
     * Returns the size of the recipe area
     */
    public int a()
    {
        return 1;
    }

    public ItemStack b()
    {
        return new ItemStack(CoverSystemProxy.blockMultipart, 1, 0);
    }

    // TODO
    public Recipe toBukkitRecipe()
    {
        return null;
    }
}
