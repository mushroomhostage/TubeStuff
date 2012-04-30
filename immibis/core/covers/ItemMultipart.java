package immibis.core.covers;

import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemBlock;
import net.minecraft.server.ItemStack;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class ItemMultipart extends ItemBlock
{
    private BlockMultipart block;

    public ItemMultipart(int var1)
    {
        super(var1);
        BlockMultipart var10001 = this.block;
        this.block = (BlockMultipart)BlockMultipart.byId[var1 + Block.byId.length];
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
    {
        return false;
    }

    public void placeInBlock(World var1, int var2, int var3, int var4, EnumPosition var5, ItemStack var6)
    {
        int var7 = var6.getData();

        if (var7 >= 0 && var7 < CoverSystemProxy.parts.size())
        {
            Object var8 = var1.getTileEntity(var2, var3, var4);

            if (var8 == null || !(var8 instanceof ICoverableTile))
            {
                if (var1.getTypeId(var2, var3, var4) != 0)
                {
                    return;
                }

                if (!var1.mayPlace(this.block.id, var2, var3, var4, true, 0))
                {
                    return;
                }

                var1.setRawTypeId(var2, var3, var4, this.block.id);
                var8 = new TileMultipart();
                var1.setTileEntity(var2, var3, var4, (TileEntity)var8);
            }

            PartType var9 = (PartType)CoverSystemProxy.parts.get(Integer.valueOf(var7));
            CoverImpl var10 = ((ICoverableTile)var8).getCoverImpl();

            if (var10.addPart(new Part(var9, var5)))
            {
                --var6.count;
                var1.applyPhysics(var2, var3, var4, this.block.id);
                var1.notify(var2, var3, var4);
            }
        }
    }

    public String a(ItemStack var1)
    {
        return "immibis.core.multipart." + var1.getData();
    }

    public boolean e()
    {
        return true;
    }
}
