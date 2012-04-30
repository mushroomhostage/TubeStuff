package immibis.core.covers;

import java.util.Iterator;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public abstract class ItemCoverableBase extends Item
{
    protected ItemCoverableBase(int var1)
    {
        super(var1);
    }

    protected boolean mergeIntoMultipartBlock(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7, int var8)
    {
        TileEntity var9 = var3.getTileEntity(var4, var5, var6);

        if (var9 != null && var9 instanceof TileMultipart)
        {
            TileMultipart var10 = (TileMultipart)var9;
            CoverImpl var11 = var10.getCoverImpl();

            if (!var3.setTypeId(var4, var5, var6, var8))
            {
                return false;
            }
            else
            {
                TileCoverableBase var12 = (TileCoverableBase)var3.getTileEntity(var4, var5, var6);
                Iterator var13 = var11.parts.iterator();

                while (var13.hasNext())
                {
                    Part var14 = (Part)var13.next();
                    var12.cover.parts.add(var14);
                }

                Block var15 = Block.byId[var8];
                var15.postPlace(var3, var4, var5, var6, var7);
                var15.postPlace(var3, var4, var5, var6, var2);
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}
