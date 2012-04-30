package immibis.core.sprites;

public class ModSpriteSheet
{
    public final String path;
    public final int count_x;
    public final int count_y;
    int size;
    int[] indices = new int[256];
    int[] used_indices;
    String file;

    public ModSpriteSheet(String var1, int var2, int var3, int[] var4)
    {
        this.path = var1;
        this.count_x = var2;
        this.count_y = var3;
        this.used_indices = var4;
        this.size = -1;
    }

    public final int getIndex(int var1)
    {
        return this.indices[var1];
    }

    public final String getFile()
    {
        return this.file;
    }
}
