package immibis.core.covers;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;

public class Part
{
    public final PartType type;
    public final double size;
    public final EnumPosition pos;
    protected AxisAlignedBB aabb;

    public Part(PartType var1, EnumPosition var2)
    {
        this.type = var1;
        this.size = var1.size;
        this.pos = var2;
    }

    private static double getMinFromAxisPosition(EnumAxisPosition var0, double var1)
    {
        switch (Part.NamelessClass776900065.$SwitchMap$immibis$core$covers$EnumAxisPosition[var0.ordinal()])
        {
            case 1:
            case 2:
                return 0.0D;

            case 3:
                return 1.0D - var1;

            case 4:
                return (1.0D - var1) / 2.0D;

            default:
                throw new IllegalArgumentException("ap");
        }
    }

    private static double getMaxFromAxisPosition(EnumAxisPosition var0, double var1)
    {
        switch (Part.NamelessClass776900065.$SwitchMap$immibis$core$covers$EnumAxisPosition[var0.ordinal()])
        {
            case 1:
            case 3:
                return 1.0D;

            case 2:
                return var1;

            case 4:
                return (1.0D + var1) / 2.0D;

            default:
                throw new IllegalArgumentException("ap");
        }
    }

    private double getMinFromAxisPosition(EnumAxisPosition var1)
    {
        switch (Part.NamelessClass776900065.$SwitchMap$immibis$core$covers$EnumAxisPosition[var1.ordinal()])
        {
            case 1:
            case 2:
                return 0.0D;

            case 3:
                return 1.0D - this.size;

            case 4:
                return (1.0D - this.size) / 2.0D;

            default:
                throw new IllegalArgumentException("ap");
        }
    }

    private double getMaxFromAxisPosition(EnumAxisPosition var1)
    {
        switch (Part.NamelessClass776900065.$SwitchMap$immibis$core$covers$EnumAxisPosition[var1.ordinal()])
        {
            case 1:
            case 3:
                return 1.0D;

            case 2:
                return this.size;

            case 4:
                return (1.0D + this.size) / 2.0D;

            default:
                throw new IllegalArgumentException("ap");
        }
    }

    public AxisAlignedBB getBoundingBox()
    {
        if (this.aabb == null)
        {
            this.aabb = getBoundingBox(this.pos, this.size);
        }

        return this.aabb;
    }

    public static AxisAlignedBB getBoundingBox(EnumPosition var0, double var1)
    {
        double var3 = getMinFromAxisPosition(var0.x, var1);
        double var5 = getMinFromAxisPosition(var0.y, var1);
        double var7 = getMinFromAxisPosition(var0.z, var1);
        double var9 = getMaxFromAxisPosition(var0.x, var1);
        double var11 = getMaxFromAxisPosition(var0.y, var1);
        double var13 = getMaxFromAxisPosition(var0.z, var1);
        return AxisAlignedBB.a(var3, var5, var7, var9, var11, var13);
    }

    public NBTBase writeToNBT()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        var1.setInt("type", this.type.id);
        var1.setByte("pos", (byte)this.pos.ordinal());
        return var1;
    }

    public static Part readFromNBT(NBTBase var0)
    {
        NBTTagCompound var1 = (NBTTagCompound)var0;
        PartType var2 = (PartType)CoverSystemProxy.parts.get(Integer.valueOf(var1.getInt("type")));
        EnumPosition var3 = EnumPosition.values()[var1.getByte("pos")];
        Part var4 = new Part(var2, var3);
        return var4;
    }

    static class NamelessClass776900065
    {
        static final int[] $SwitchMap$immibis$core$covers$EnumAxisPosition = new int[EnumAxisPosition.values().length];

        static
        {
            try
            {
                $SwitchMap$immibis$core$covers$EnumAxisPosition[EnumAxisPosition.Span.ordinal()] = 1;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                $SwitchMap$immibis$core$covers$EnumAxisPosition[EnumAxisPosition.Negative.ordinal()] = 2;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                $SwitchMap$immibis$core$covers$EnumAxisPosition[EnumAxisPosition.Positive.ordinal()] = 3;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                $SwitchMap$immibis$core$covers$EnumAxisPosition[EnumAxisPosition.Centre.ordinal()] = 4;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }
}
