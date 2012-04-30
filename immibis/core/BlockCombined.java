package immibis.core;

import forge.ITextureProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityHuman;
import net.minecraft.src.EntityItem;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public abstract class BlockCombined extends BlockContainer implements ITextureProvider
{
    private static Random random = new Random();
    public ItemCombined item;
    public final String texfile;

    /**
     * Return the state of blocks statistics flags - if the block is counted for mined and placed.
     */
    public boolean r()
    {
        return false;
    }

    public BlockCombined(int var1, Material var2, String var3)
    {
        super(var1, var2);
        this.texfile = var3;
        this.c(2.0F);
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean isPowerSource()
    {
        return true;
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean a(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        TileCombined var6 = (TileCombined)var1.getTileEntity(var2, var3, var4);
        return var6 != null ? var6.redstone_output : false;
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean d(World var1, int var2, int var3, int var4, int var5)
    {
        TileCombined var6 = (TileCombined)var1.getTileEntity(var2, var3, var4);
        return var6 != null ? var6.redstone_output : false;
    }

    public ArrayList getBlockDropped(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        ArrayList var7 = new ArrayList();
        var7.add(new ItemStack(this, 1, var5));
        TileEntity var8 = var1.getTileEntity(var2, var3, var4);

        if (var8 != null && var8 instanceof TileBasicInventory)
        {
            BasicInventory var9 = ((TileBasicInventory)var8).inv;
            ItemStack[] var10 = var9.contents;
            int var11 = var10.length;

            for (int var12 = 0; var12 < var11; ++var12)
            {
                ItemStack var13 = var10[var12];

                if (var13 != null)
                {
                    var7.add(var13);
                }
            }
        }

        return var7;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public abstract int a(int var1, int var2);

    /**
     * The type of render function that is called for this block
     */
    public int c()
    {
        return 0;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        if (!var1.isStatic)
        {
            TileCombined var6 = (TileCombined)var1.getTileEntity(var2, var3, var4);

            if (var6 != null)
            {
                var6.onBlockNeighbourChange();
            }
        }
    }

    /**
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    public boolean interact(World var1, int var2, int var3, int var4, EntityHuman var5)
    {
        TileCombined var6 = (TileCombined)var1.getTileEntity(var2, var3, var4);
        return var6 != null ? var6.onBlockActivated(var5) : false;
    }

    /**
     * Called whenever the block is removed.
     */
    public void remove(World var1, int var2, int var3, int var4)
    {
        TileCombined var5 = (TileCombined)var1.getTileEntity(var2, var3, var4);

        if (var5 != null)
        {
            var5.onBlockRemoval();
        }

        ArrayList var6 = this.getBlockDropped(var1, var2, var3, var4, -1, -1);
        var6.remove(0);
        Iterator var7 = var6.iterator();

        while (var7.hasNext())
        {
            ItemStack var8 = (ItemStack)var7.next();
            float var9 = (float)var2 + random.nextFloat() * 0.8F + 0.1F;
            float var10 = (float)var3 + random.nextFloat() * 0.8F + 0.1F;
            float var11 = (float)var4 + random.nextFloat() * 0.8F + 0.1F;
            System.out.println("drop " + var8 + " at " + var9 + "," + var10 + "," + var11);
            EntityItem var14;

            for (int var12 = var8.count; var12 > 0; var1.addEntity(var14))
            {
                int var13 = Math.min(random.nextInt(21) + 10, var12);
                var12 -= var13;
                var14 = new EntityItem(var1, (double)var9, (double)var10, (double)var11, new ItemStack(var8.id, var13, var8.getData()));
                var14.motX = random.nextGaussian() * 0.05000000074505806D;
                var14.motY = random.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
                var14.motZ = random.nextGaussian() * 0.05000000074505806D;

                if (var8.hasTag())
                {
                    var14.itemStack.setTag(var8.getTag());
                }
            }
        }

        super.remove(var1, var2, var3, var4);
    }

    public abstract TileEntity getBlockEntity(int var1);

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity a_()
    {
        throw new UnsupportedOperationException("");
    }

    public String getTextureFile()
    {
        return this.texfile;
    }

    public abstract void addCreativeItems(ArrayList var1);
}
