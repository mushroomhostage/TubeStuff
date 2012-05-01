package immibis.core;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;

import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import java.util.List;
import java.util.ArrayList;

public class TileBasicInventory extends TileCombined implements IInventory
{
    public BasicInventory inv;
    public String invname;
    public List transaction;

    public TileBasicInventory(int var1, String var2)
    {
        this.inv = new BasicInventory(var1);
        this.invname = var2;
        this.transaction = new ArrayList();
    }

    public void onOpen(CraftHumanEntity crafthumanentity)
    {
        transaction.add(crafthumanentity);
    }

    public void onClose(CraftHumanEntity crafthumanentity)
    {
        transaction.remove(crafthumanentity);
    }

    public List getViewers()
    {
        return transaction;
    }

    public void setMaxStackSize(int i)
    {
    }

    public ItemStack[] getContents()
    {
        return this.inv.contents;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return this.inv.contents.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int var1)
    {
        return this.inv.contents[var1];
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack splitStack(int var1, int var2)
    {
        return this.inv.decrStackSize(var1, var2);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int var1, ItemStack var2)
    {
        this.inv.contents[var1] = var2;
        this.update();
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return this.invname;
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

    public void f() {}

    public void g() {}

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound var1)
    {
        super.a(var1);
        this.inv.readFromNBT(var1.getList("Items"));
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound var1)
    {
        super.b(var1);
        var1.set("Items", this.inv.writeToNBT());
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack splitWithoutUpdate(int var1)
    {
        ItemStack var2 = this.getItem(var1);
        this.setItem(var1, (ItemStack)null);
        return var2;
    }
}
