package immibis.core;

import net.minecraft.src.EntityHuman;
import net.minecraft.src.TileEntity;

public abstract class TileCombined extends TileEntity
{
    public boolean redstone_output = false;

    public void onBlockNeighbourChange() {}

    public boolean onBlockActivated(EntityHuman var1)
    {
        return false;
    }

    public void onBlockRemoval() {}

    public void notifyNeighbouringBlocks()
    {
        this.world.applyPhysics(this.x, this.y, this.z, this.world.getTypeId(this.x, this.y, this.z));
    }
}
