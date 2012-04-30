package immibis.core.covers;

import java.util.Iterator;
import net.minecraft.server.Block;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.TileEntity;

public abstract class TileCoverableBase extends TileEntity implements ICoverableTile
{
    protected CoverImpl cover;
    private Part fakeCentrePart;

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound var1)
    {
        super.b(var1);
        this.cover.writeToNBT(var1);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound var1)
    {
        super.a(var1);
        this.cover.readFromNBT(var1);
    }

    public TileCoverableBase(BlockCoverableBase var1, double var2)
    {
        this.cover = new CoverImpl(this, var2);
        this.fakeCentrePart = new Part(new PartType(EnumPartClass.Centre, 0.5D, "Fake centre part for wrapped block", -1.0F, Block.REDSTONE_WIRE, 0), EnumPosition.Centre);
        this.cover.wrappedBlock = var1;
    }

    public boolean isSideOpen(int var1)
    {
        return this.cover.isSideOpen(var1);
    }

    public CoverImpl getCoverImpl()
    {
        return this.cover;
    }

    public boolean isSideOpen(int var1, int var2, int var3)
    {
        return this.cover.isSideOpen(this.x, this.y, this.z, var1, var2, var3);
    }

    public Part getFakeCentrePart()
    {
        return this.fakeCentrePart;
    }

    public void convertToMultipartBlockInPlace()
    {
        if (this.cover.parts.size() == 0)
        {
            this.world.setTypeId(this.x, this.y, this.z, 0);
        }
        else
        {
            this.world.setTypeId(this.x, this.y, this.z, CoverSystemProxy.blockMultipart.id);
            CoverImpl var1 = ((ICoverableTile)this.world.getTileEntity(this.x, this.y, this.z)).getCoverImpl();
            Iterator var2 = this.cover.parts.iterator();

            while (var2.hasNext())
            {
                Part var3 = (Part)var2.next();
                var1.parts.add(var3);
            }
        }
    }
}
