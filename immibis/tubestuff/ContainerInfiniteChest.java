package immibis.tubestuff;

import immibis.core.BasicInventory;
import java.util.Iterator;
import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ICrafting;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoader;
import net.minecraft.server.Slot;

public class ContainerInfiniteChest extends Container
{
    private EntityHuman player;
    public TileInfiniteChest chest;
    public int page;
    public static final int WIDTH = 13;
    public static final int HEIGHT = 6;
    private int lastPage = -1;
    private int lastMaxPages = -1;

    public EntityHuman getPlayer()
    {
        return player;
    }

    public IInventory getInventory()
    {
        return chest;
    }

    public void setPage(int var1)
    {
        this.page = var1;
        int var2 = var1 * 13 * 6;
        this.e.clear();
        int var3;
        int var4;

        for (var3 = 0; var3 < 6; ++var3)
        {
            for (var4 = 0; var4 < 13; ++var4)
            {
                this.a(new Slot(this.chest, var2 + var4 + var3 * 13, var4 * 18 + 5, var3 * 18 + 4));
            }
        }

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (var4 = 0; var4 < 9; ++var4)
            {
                this.a(new Slot(this.player.inventory, var4 + var3 * 9 + 9, var4 * 18 + 5, var3 * 18 + 123));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.a(new Slot(this.player.inventory, var3, var3 * 18 + 5, 181));
        }

        this.chest.update();
    }

    public ContainerInfiniteChest(EntityHuman var1, TileInfiniteChest var2)
    {
        this.player = var1;
        this.chest = var2;
        this.setPage(0);
    }

    public ItemStack clickItem(int var1, int var2, boolean var3, EntityHuman var4)
    {
        if (var2 == 3)
        {
            if (this.page > 0)
            {
                this.setPage(this.page - 1);
            }

            return null;
        }
        else if (var2 == 4)
        {
            if (this.page < this.chest.maxPages - 1)
            {
                this.setPage(this.page + 1);
            }

            return null;
        }
        else
        {
            return super.clickItem(var1, var2, var3, var4);
        }
    }

    public void addSlotListener(ICrafting var1)
    {
        super.addSlotListener(var1);
        var1.setContainerData(this, 0, this.page);
        var1.setContainerData(this, 1, this.chest.maxPages);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void a()
    {
        super.a();
        Iterator var1 = this.listeners.iterator();

        while (var1.hasNext())
        {
            ICrafting var2 = (ICrafting)var1.next();

            if (this.lastPage != this.page)
            {
                var2.setContainerData(this, 0, this.page);
            }

            if (this.lastMaxPages != this.chest.maxPages)
            {
                var2.setContainerData(this, 1, this.chest.maxPages);
            }
        }

        this.lastPage = this.page;
        this.lastMaxPages = this.chest.maxPages;
    }

    public boolean b(EntityHuman var1)
    {
        return this.chest.a(var1);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack a(int var1)
    {
        Slot var2 = (Slot)this.e.get(var1);

        if (var2 == null)
        {
            return null;
        }
        else
        {
            try
            {
                int var3 = ((Integer)ModLoader.getPrivateValue(Slot.class, var2, 0)).intValue();

                if (var2.inventory == this.player.inventory)
                {
                    BasicInventory.mergeStackIntoRange(this.player.inventory, this.chest, var3, 0, this.chest.items.size() + 1);
                }
                else if (var2.inventory == this.chest)
                {
                    BasicInventory.mergeStackIntoRange(this.chest, this.player.inventory, var3, 0, 36);
                }

                return null;
            }
            catch (Exception var4)
            {
                return null;
            }
        }
    }
}
