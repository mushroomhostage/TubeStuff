package immibis.tubestuff;

import immibis.core.GuiSystem;
import immibis.core.TileCombined;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;

public class TileInfiniteChest extends TileCombined implements IInventory
{
    public List items = new ArrayList();
    int maxPages = 1;
    public static final int PAGESIZE = 78;

    public void updateMaxPages()
    {
        while (this.items.size() > 0 && this.items.get(this.items.size() - 1) == null)
        {
            this.items.remove(this.items.size() - 1);
        }

        this.maxPages = Math.max(1, (this.items.size() + 78) / 78);
    }

    public boolean canUpdate()
    {
        return false;
    }

    public boolean onBlockActivated(EntityHuman var1)
    {
        GuiSystem.OpenStringGUI(TileInfiniteChest.class, "gui", var1, new ContainerInfiniteChest(var1.inventory, this), this);
        return true;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return (this.items.size() + 78) / 78 * 78;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int var1)
    {
        return var1 < this.items.size() ? (ItemStack)this.items.get(var1) : null;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack splitStack(int var1, int var2)
    {
        if (this.items.get(var1) == null)
        {
            return null;
        }
        else if (((ItemStack)this.items.get(var1)).count <= var2)
        {
            ItemStack var3 = (ItemStack)this.items.get(var1);
            this.items.set(var1, (Object)null);
            this.updateMaxPages();
            return var3;
        }
        else
        {
            return ((ItemStack)this.items.get(var1)).a(var2);
        }
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound var1)
    {
        super.a(var1);
        NBTTagList var2 = var1.getList("Items");
        this.items = new ArrayList(var2.size());

        for (int var3 = 0; var3 < var2.size(); ++var3)
        {
            this.items.add(ItemStack.a((NBTTagCompound)var2.get(var3)));
        }

        var1.set("Items", var2);
        this.updateMaxPages();
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound var1)
    {
        super.b(var1);
        NBTTagList var2 = new NBTTagList("Items");

        for (int var3 = 0; var3 < this.items.size(); ++var3)
        {
            ItemStack var4 = (ItemStack)this.items.get(var3);

            if (var4 != null)
            {
                NBTTagCompound var5 = new NBTTagCompound();
                var4.save(var5);
                var2.add(var5);
            }
        }

        var1.set("Items", var2);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int var1, ItemStack var2)
    {
        var1 = Math.min(var1, this.items.size());

        if (var1 == this.items.size())
        {
            this.items.add(var2);
        }
        else
        {
            this.items.set(var1, var2);
        }

        this.updateMaxPages();
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "Black hole chest";
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getMaxStackSize()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a(EntityHuman var1)
    {
        if (this.world.getTileEntity(this.x, this.y, this.z) != this)
        {
            return false;
        }
        else
        {
            double var2 = var1.e((double)this.x + 0.5D, (double)this.y + 0.5D, (double)this.z + 0.5D);
            return var2 <= 64.0D;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack splitWithoutUpdate(int var1)
    {
        return null;
    }

    public void f() {}

    public void g() {}
}
