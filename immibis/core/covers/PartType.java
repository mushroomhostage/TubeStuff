package immibis.core.covers;

import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;

public class PartType
{
    public final EnumPartClass clazz;
    public final double size;
    public final String name;
    public int[] textures;
    protected int id;
    public float hardness;
    public String texfile = "/terrain.png";
    private static final int[] DEFAULT_TEXTURES = new int[] {0, 1, 2, 3, 4, 5};
    public Block modelBlock;
    public int modelMeta;
    private double u;
    private double v;

    public boolean canHarvestCover(EntityHuman var1)
    {
        return this.modelBlock.canHarvestBlock(var1, this.modelMeta);
    }

    public PartType(EnumPartClass var1, double var2, String var4, float var5, Block var6, int var7)
    {
        this.clazz = var1;
        this.size = var2;
        this.name = var4;
        this.hardness = var5;
        this.modelBlock = var6;
        this.modelMeta = var7;
        this.textures = DEFAULT_TEXTURES;
    }
}
