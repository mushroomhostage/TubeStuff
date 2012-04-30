package immibis.tubestuff;

import immibis.core.BlockCombined;
import immibis.core.TileCombined;
import java.util.ArrayList;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;
import net.minecraft.server.mod_TubeStuff;

public class BlockTubestuff extends BlockCombined
{
    public static int model;

    public BlockTubestuff(int var1)
    {
        super(var1, Material.ORE, mod_TubeStuff.sprites.getFile());
        this.c(2.0F);
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean a(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        TileCombined var6 = (TileCombined)var1.getTileEntity(var2, var3, var4);
        return var6 instanceof TileCraftingTable ? false : (var6 != null ? var6.redstone_output : false);
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean d(World var1, int var2, int var3, int var4, int var5)
    {
        TileCombined var6 = (TileCombined)var1.getTileEntity(var2, var3, var4);
        return var6 instanceof TileCraftingTable ? false : (var6 != null ? var6.redstone_output : false);
    }

    /**
     * The type of render function that is called for this block
     */
    public int c()
    {
        return model;
    }

    public TileEntity getBlockEntity(int var1)
    {
        return (TileEntity)(var1 == 0 ? new TileBuffer() : (var1 == 1 ? new TileCraftingTable() : (var1 == 2 ? new TileInfiniteChest() : null)));
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int var1, int var2)
    {
        byte var3 = 0;

        if (var2 == 0)
        {
            var3 = 3;
        }
        else if (var2 == 1)
        {
            if (var1 == 0)
            {
                var3 = 1;
            }
            else if (var1 == 1)
            {
                var3 = 0;
            }
            else
            {
                var3 = 2;
            }
        }
        else if (var2 == 2)
        {
            var3 = 4;
        }

        return mod_TubeStuff.sprites.getIndex(var3);
    }

    public void addCreativeItems(ArrayList var1)
    {
        var1.add(new ItemStack(this, 1, 0));
        var1.add(new ItemStack(this, 1, 1));
        var1.add(new ItemStack(this, 1, 2));
    }
}
