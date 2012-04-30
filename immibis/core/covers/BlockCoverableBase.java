package immibis.core.covers;

import java.util.ArrayList;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Material;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public abstract class BlockCoverableBase extends BlockMultipart
{
    static boolean useRealRenderType = false;

    protected BlockCoverableBase(int var1, Material var2)
    {
        super(var1, var2, CoverSystemProxy.coverModel);
    }

    public int getRenderTypeMultipart()
    {
        return 0;
    }

    public MovingObjectPosition collisionRayTraceMultipart(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6)
    {
        return this.defaultCollisionRayTrace(var1, var2, var3, var4, var5, var6);
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public final MovingObjectPosition a(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6)
    {
        MovingObjectPosition var7 = super.a(var1, var2, var3, var4, var5, var6);

        if (var7 != null)
        {
            return var7;
        }
        else
        {
            var7 = this.collisionRayTraceMultipart(var1, var2, var3, var4, var5, var6);

            if (var7 != null)
            {
                var7.subHit = -2;
                return var7;
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * The type of render function that is called for this block
     */
    public final int c()
    {
        return useRealRenderType ? this.getRenderTypeMultipart() : super.c();
    }

    /**
     * Adds to the supplied array any colliding bounding boxes with the passed in bounding box. Args: world, x, y, z,
     * axisAlignedBB, arrayList
     */
    public final void a(World var1, int var2, int var3, int var4, AxisAlignedBB var5, ArrayList var6)
    {
        super.a(var1, var2, var3, var4, var5, var6);
        this.getCollidingBoundingBoxesMultipart(var1, var2, var3, var4, var5, var6);
    }

    public void getCollidingBoundingBoxesMultipart(World var1, int var2, int var3, int var4, AxisAlignedBB var5, ArrayList var6)
    {
        AxisAlignedBB var7 = this.e(var1, var2, var3, var4);

        if (var7 != null && var7.a(var5))
        {
            var6.add(var7);
        }
    }
}
