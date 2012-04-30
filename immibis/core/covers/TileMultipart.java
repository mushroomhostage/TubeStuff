package immibis.core.covers;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;

public class TileMultipart extends TileEntity implements ICoverableTile
{
    public CoverImpl cover = new CoverImpl(this);

    /**
     * Overriden in a sign to provide the text
     */
    public final Packet d()
    {
        return this.cover.getDefaultDescriptionPacket();
    }

    public CoverImpl getCoverImpl()
    {
        return this.cover;
    }

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
}
