package immibis.tubestuff;

import forge.ForgeHooks;
import forge.ISidedInventory;
import immibis.core.GuiSystem;
import immibis.core.TileBasicInventory;
import java.util.Iterator;
import java.util.logging.Level;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.CraftingRecipe;
import net.minecraft.src.EntityHuman;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;

public class TileCraftingTable extends TileBasicInventory implements ISidedInventory
{
    static final int SLOT_OUTPUT = 0;
    static final int START_RECIPE = 1;
    static final int SIZE_RECIPE = 9;
    static final int START_INPUT = 10;
    static final int SIZE_INPUT = 45;
    static final int START_OVERFLOW = 46;
    static final int SIZE_OVERFLOW = 9;
    static final int INVSIZE = 55;
    private CraftingRecipe cachedRecipe;
    private boolean invChanged = true;
    private boolean recipeChanged = true;
    private boolean outputFull = false;
    private boolean insufficientInput = false;
    private int pulse_ticks = 0;
    private static boolean allowCraftingHook = true;

    public int getStartInventorySide(int var1)
    {
        switch (var1)
        {
            case 0:
                return 46;

            case 1:
                return 0;

            default:
                return 10 + 11 * (var1 - 2);
        }
    }

    public int getSizeInventorySide(int var1)
    {
        return var1 == 1 ? 1 : 9;
    }

    public TileCraftingTable()
    {
        super(55, "ACT Mk II");
    }

    private InventoryCrafting makeInventoryCrafting(int var1)
    {
        InventoryCraftingACT2 var2 = new InventoryCraftingACT2();

        for (int var3 = 0; var3 < 9; ++var3)
        {
            var2.setItem(var3, this.inv.contents[var3 + var1]);
        }

        return var2;
    }

    private void cacheOutput()
    {
        InventoryCrafting var1 = this.makeInventoryCrafting(1);
        Iterator var2 = CraftingManager.getInstance().getRecipies().iterator();
        CraftingRecipe var3;

        do
        {
            if (!var2.hasNext())
            {
                this.cachedRecipe = null;
                return;
            }

            var3 = (CraftingRecipe)var2.next();
        }
        while (!var3.a(var1));

        this.cachedRecipe = var3;
    }

    public static final boolean areItemsEqual(ItemStack var0, ItemStack var1)
    {
        return var1.id == var0.id && (!var1.usesData() || var1.getData() == var0.getData());
    }

    private void makeOutput()
    {
        if (this.inv.contents[0] == null)
        {
            if (this.cachedRecipe != null)
            {
                int[] var1 = new int[9];
                int[] var2 = new int[45];

                for (int var3 = 0; var3 < 9; ++var3)
                {
                    ItemStack var4 = this.inv.contents[var3 + 1];
                    var1[var3] = -1;

                    if (var4 != null)
                    {
                        for (int var5 = 0; var5 < 45; ++var5)
                        {
                            ItemStack var6 = this.inv.contents[var5 + 10];

                            if (var6 != null && var2[var5] < var6.count && areItemsEqual(var4, var6))
                            {
                                var1[var3] = var5;
                                ++var2[var5];
                                break;
                            }
                        }

                        if (var1[var3] == -1)
                        {
                            this.insufficientInput = true;
                            return;
                        }
                    }
                }

                InventoryCraftingACT2 var7 = new InventoryCraftingACT2();
                int var8;

                for (var8 = 0; var8 < 9; ++var8)
                {
                    if (var1[var8] != -1)
                    {
                        var7.setItem(var8, this.inv.contents[var1[var8] + 10]);
                    }
                }

                if (!this.cachedRecipe.a(var7))
                {
                    this.insufficientInput = true;
                }
                else
                {
                    for (var8 = 0; var8 < 9; ++var8)
                    {
                        if (var1[var8] != -1)
                        {
                            var7.setItem(var8, this.inv.decrStackSize(var1[var8] + 10, 1));
                        }
                    }

                    this.inv.contents[0] = this.cachedRecipe.b(var7);
                    this.decreaseInput(var7, this.inv.contents[0]);

                    for (var8 = 0; var8 < 9; ++var8)
                    {
                        ItemStack var9 = var7.getItem(var8);

                        if (var9 != null)
                        {
                            this.inv.mergeStackIntoRange(var9, 46, 55);
                        }
                    }

                    this.invChanged = true;
                    this.update();
                }
            }
        }
    }

    private void decreaseInput(InventoryCrafting var1, ItemStack var2)
    {
        if (allowCraftingHook)
        {
            try
            {
                ModLoader.takenFromCrafting((EntityHuman)null, var2, var1);
                ForgeHooks.onTakenFromCrafting((EntityHuman)null, var2, var1);
                Item.byId[var2.id].d(var2, this.world, (EntityHuman)null);
            }
            catch (Exception var6)
            {
                allowCraftingHook = false;
                var6.printStackTrace();
                ModLoader.getLogger().log(Level.WARNING, "TubeStuff: This happened when trying to call a crafting hook with a null player. I won\'t try that again, but this may cause some bugs. If you can tell which mod caused the problem, bug its author to fix it.");
            }
        }

        for (int var3 = 0; var3 < var1.getSize(); ++var3)
        {
            ItemStack var4 = var1.getItem(var3);

            if (var4 != null)
            {
                var1.splitStack(var3, 1);

                if (var4.getItem().k())
                {
                    ItemStack var5 = new ItemStack(var4.getItem().j());

                    if (var1.getItem(var3) == null)
                    {
                        var1.setItem(var3, var5);
                    }
                    else
                    {
                        this.inv.mergeStackIntoRange(var5, 46, 55);
                    }
                }
            }
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void q_()
    {
        if (this.inv.contents[0] == null)
        {
            if (this.recipeChanged)
            {
                this.cacheOutput();
                this.recipeChanged = false;
            }

            if (this.cachedRecipe != null && !this.insufficientInput)
            {
                this.makeOutput();
            }
        }

        if (this.inv.contents[0] != null)
        {
            this.pulse_ticks = (this.pulse_ticks + 1) % 20;

            if (this.pulse_ticks == 0)
            {
                this.redstone_output = !this.redstone_output;
                this.notifyNeighbouringBlocks();
            }
        }
        else if (this.redstone_output)
        {
            this.redstone_output = false;
            this.notifyNeighbouringBlocks();
        }

        if (this.invChanged)
        {
            this.invChanged = false;
            this.insufficientInput = false;
        }
    }

    public boolean onBlockActivated(EntityHuman var1)
    {
        GuiSystem.OpenStringGUI(TileCraftingTable.class, "gui", var1, new ContainerCraftingTable(var1.inventory, this), this);
        return true;
    }

    private void slotChanging(int var1)
    {
        if (var1 == 0)
        {
            this.outputFull = false;
        }

        if (var1 >= 1 && var1 < 10)
        {
            this.recipeChanged = true;
        }

        this.invChanged = true;
        this.insufficientInput = false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack splitStack(int var1, int var2)
    {
        this.slotChanging(var1);
        return super.splitStack(var1, var2);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int var1, ItemStack var2)
    {
        this.slotChanging(var1);
        super.setItem(var1, var2);
    }
}
