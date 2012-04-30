package immibis.core.covers;

import java.util.ArrayList;
import java.util.WeakHashMap;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.TileEntity;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class BlockMultipart extends BlockContainer implements ICoverableBlock
{
    private final int model;
    static AxisAlignedBB selectedBoundingBox = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    static int[] texindices = new int[] {0, 0, 0, 0, 0, 0};
    private static float hardness;
    static WeakHashMap punching_subhit = new WeakHashMap();

    /**
     * Adds to the supplied array any colliding bounding boxes with the passed in bounding box. Args: world, x, y, z,
     * axisAlignedBB, arrayList
     */
    public void a(World var1, int var2, int var3, int var4, AxisAlignedBB var5, ArrayList var6) {}

    public float blockStrengthMultipart(EntityHuman var1, int var2)
    {
        return super.blockStrength(var1, var2);
    }

    public void harvestBlockMultipart(World var1, EntityHuman var2, int var3, int var4, int var5, int var6)
    {
        super.a(var1, var2, var3, var4, var5, var6);
    }

    public boolean canHarvestBlockMultipart(EntityHuman var1, int var2)
    {
        return true;
    }

    public final boolean canHarvestBlock(EntityHuman var1, int var2)
    {
        return this.canHarvestBlockMultipart(var1, var2);
    }

    public final float getHardness(int var1)
    {
        return hardness;
    }

    public static void setPunchingSubhit(EntityHuman var0, int var1)
    {
        punching_subhit.put(var0, Integer.valueOf(var1));
    }

    static int getPunchingSubhit(EntityHuman var0)
    {
        Integer var1 = (Integer)punching_subhit.get(var0);
        return var1 == null ? -1000 : var1.intValue();
    }

    public boolean removeBlockByPlayer(World var1, EntityHuman var2, int var3, int var4, int var5)
    {
        return true;
    }

    public final float blockStrength(World var1, EntityHuman var2, int var3, int var4, int var5)
    {
        int var6 = getPunchingSubhit(var2);
        TileEntity var7 = var1.getTileEntity(var3, var4, var5);

        if (var7 != null && var7 instanceof ICoverableTile)
        {
            CoverImpl var8 = ((ICoverableTile)var7).getCoverImpl();
            int var9 = var1.getData(var3, var4, var5);

            if (var6 == -2)
            {
                return this.blockStrengthMultipart(var2, var9);
            }
            else if (var6 >= 0 && var6 < var8.parts.size())
            {
                Part var10 = (Part)var8.parts.get(var6);

                if (var10 == null)
                {
                    return 0.01F;
                }
                else
                {
                    hardness = var10.type.hardness;
                    return hardness < 0.0F ? 0.0F : (!var10.type.canHarvestCover(var2) ? 0.01F / hardness : var2.getCurrentPlayerStrVsBlock(var10.type.modelBlock, var10.type.modelMeta) / hardness / 30.0F);
                }
            }
            else
            {
                return 0.01F;
            }
        }
        else
        {
            return 0.01F;
        }
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public final void a(World var1, EntityHuman var2, int var3, int var4, int var5, int var6)
    {
        int var7 = getPunchingSubhit(var2);

        try
        {
            ((ICoverableTile)var1.getTileEntity(var3, var4, var5)).getCoverImpl().harvestBlock(var1, var2, var3, var4, var5, var7);
        }
        catch (ClassCastException var9)
        {
            var1.setTypeId(var3, var4, var5, 0);
        }
    }

    /**
     * Spawns EntityItem in the world for the given ItemStack if the world is not remote.
     */
    public void a(World var1, int var2, int var3, int var4, ItemStack var5)
    {
        super.a(var1, var2, var3, var4, var5);
    }

    void setAABB(AxisAlignedBB var1)
    {
        this.minX = var1.a;
        this.minY = var1.b;
        this.minZ = var1.c;
        this.maxX = var1.d;
        this.maxY = var1.e;
        this.maxZ = var1.f;
    }

    protected BlockMultipart(int var1, Material var2, int var3)
    {
        super(var1, var2);
        this.model = var3;
    }

    MovingObjectPosition defaultCollisionRayTrace(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6)
    {
        return super.a(var1, var2, var3, var4, var5, var6);
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition a(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6)
    {
        try
        {
            return ((ICoverableTile)var1.getTileEntity(var2, var3, var4)).getCoverImpl().collisionRayTrace(var1, var2, var3, var4, var5, var6);
        }
        catch (ClassCastException var8)
        {
            var1.setTypeId(var2, var3, var4, 0);
            return super.a(var1, var2, var3, var4, var5, var6);
        }
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public final boolean a()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public final boolean b()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int c()
    {
        return this.model;
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity a_()
    {
        return new TileMultipart();
    }
}
