package immibis.tubestuff;

import immibis.core.BasicInventory;
import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoader;
import net.minecraft.server.Slot;

public class ContainerCraftingTable extends Container
{
    private EntityHuman player;
    private IInventory table;
    private int invStart;

    public EntityHuman getPlayer()
    {
        return this.player;
    }

    public IInventory getInventory()
    {
        return this.table;
    }

    public ContainerCraftingTable(EntityHuman var1, IInventory var2)
    {
        this.player = var1;
        this.table = var2;
        this.a(new Slot(var2, 0, 134, 124));
        int var3;
        int var4;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (var4 = 0; var4 < 3; ++var4)
            {
                this.a(new Slot(var2, var3 + var4 * 3 + 1, var3 * 18 + 31, var4 * 18 + 107));
            }
        }

        for (var3 = 0; var3 < 5; ++var3)
        {
            for (var4 = 0; var4 < 9; ++var4)
            {
                this.a(new Slot(var2, var4 + var3 * 9 + 10, var4 * 18 + 13, var3 * 19 + 6));
            }
        }

        this.invStart = this.e.size();

        for (var3 = 0; var3 < 9; ++var3)
        {
            for (var4 = 0; var4 < 3; ++var4)
            {
                this.a(new Slot(var1.inventory, var3 + var4 * 9 + 9, var3 * 18 + 13, var4 * 18 + 168));
            }

            this.a(new Slot(var1.inventory, var3, var3 * 18 + 13, 226));
        }
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack a(int var1)
    {
        Slot var2 = (Slot)this.e.get(var1);

        try
        {
            int var3 = ((Integer)ModLoader.getPrivateValue(Slot.class, var2, 0)).intValue();

            if (var1 < this.invStart)
            {
                BasicInventory.mergeStackIntoRange(this.table, this.player.inventory, var3, 0, 36);
            }
            else
            {
                BasicInventory.mergeStackIntoRange(this.player.inventory, this.table, var3, 10, 55);
            }

            return null;
        }
        catch (Exception var4)
        {
            return null;
        }
    }

    public boolean b(EntityHuman var1)
    {
        return this.table.a(var1);
    }
}
