package immibis.core;

import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;

public class BasicInventory
{
    public ItemStack[] contents;

    public BasicInventory(int var1)
    {
        this.contents = new ItemStack[var1];
    }

    public ItemStack decrStackSize(int var1, int var2)
    {
        if (this.contents[var1] == null)
        {
            return null;
        }
        else if (this.contents[var1].count <= var2)
        {
            ItemStack var3 = this.contents[var1];
            this.contents[var1] = null;
            return var3;
        }
        else
        {
            return this.contents[var1].a(var2);
        }
    }

    public boolean mergeStackIntoRange(int var1, int var2, int var3)
    {
        ItemStack var4 = this.contents[var1];

        if (var4 == null)
        {
            return false;
        }
        else
        {
            boolean var5 = false;
            int var6;
            ItemStack var7;

            for (var6 = var2; var6 < var3; ++var6)
            {
                var7 = this.contents[var6];

                if (var7 != null && var7.id == var4.id && var7.getData() == var4.getData())
                {
                    int var8 = var7.getMaxStackSize();

                    if (var7.count + var4.count <= var8)
                    {
                        var7.count += var4.count;
                        this.contents[var1] = null;
                        return true;
                    }

                    int var9 = var8 - var7.count;

                    if (var9 > 0)
                    {
                        var7.count += var9;
                        var4.count -= var9;
                        var5 = true;
                    }
                }
            }

            for (var6 = var2; var6 < var3; ++var6)
            {
                var7 = this.contents[var6];

                if (var7 == null)
                {
                    this.contents[var6] = var4;
                    this.contents[var1] = null;
                    return true;
                }
            }

            return var5;
        }
    }

    public ItemStack mergeStackIntoRange(ItemStack var1, int var2, int var3)
    {
        if (var1 == null)
        {
            return null;
        }
        else
        {
            int var4;
            ItemStack var5;

            for (var4 = var2; var4 < var3; ++var4)
            {
                var5 = this.contents[var4];

                if (var5 != null && var5.id == var1.id && var5.getData() == var1.getData())
                {
                    int var6 = var5.getMaxStackSize();

                    if (var5.count + var1.count < var6)
                    {
                        var5.count += var1.count;
                        return null;
                    }

                    int var7 = var6 - var5.count;

                    if (var7 > 0)
                    {
                        var5.count += var7;
                        var1.count -= var7;
                    }
                }
            }

            for (var4 = var2; var4 < var3; ++var4)
            {
                var5 = this.contents[var4];

                if (var5 == null)
                {
                    this.contents[var4] = var1;
                    return null;
                }
            }

            return var1;
        }
    }

    public static boolean mergeStackIntoRange(IInventory var0, IInventory var1, int var2, int var3, int var4)
    {
        ItemStack var5 = var0.getItem(var2);

        if (var5 == null)
        {
            return false;
        }
        else
        {
            boolean var6 = false;
            int var7;
            ItemStack var8;

            for (var7 = var3; var7 < var4; ++var7)
            {
                var8 = var1.getItem(var7);

                if (var8 != null && var8.id == var5.id && var8.getData() == var5.getData())
                {
                    int var9 = var8.getMaxStackSize();

                    if (var8.count + var5.count < var9)
                    {
                        var8.count += var5.count;
                        var0.setItem(var2, (ItemStack)null);
                        var1.setItem(var7, var8);
                        return true;
                    }

                    int var10 = var9 - var8.count;

                    if (var10 > 0)
                    {
                        var8.count += var10;
                        var1.setItem(var7, var8);
                        var5.count -= var10;
                        var6 = true;
                    }
                }
            }

            for (var7 = var3; var7 < var4; ++var7)
            {
                var8 = var1.getItem(var7);

                if (var8 == null)
                {
                    var1.setItem(var7, var5);
                    var0.setItem(var2, (ItemStack)null);
                    return true;
                }
            }

            var0.setItem(var2, var5);
            return var6;
        }
    }

    public void readFromNBT(NBTTagList var1)
    {
        int var2;

        for (var2 = 0; var2 < this.contents.length; ++var2)
        {
            this.contents[var2] = null;
        }

        for (var2 = 0; var2 < var1.size(); ++var2)
        {
            NBTTagCompound var3 = (NBTTagCompound)var1.get(var2);
            int var4 = var3.getByte("Slot") & 255;

            if (var4 >= 0 && var4 < this.contents.length)
            {
                this.contents[var4] = ItemStack.a(var3);
            }
        }
    }

    public NBTTagList writeToNBT()
    {
        NBTTagList var1 = new NBTTagList("Items");

        for (int var2 = 0; var2 < this.contents.length; ++var2)
        {
            if (this.contents[var2] != null)
            {
                NBTTagCompound var3 = new NBTTagCompound();
                this.contents[var2].save(var3);
                var3.setByte("Slot", (byte)var2);
                var1.add(var3);
            }
        }

        return var1;
    }
}
