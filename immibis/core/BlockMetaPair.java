package immibis.core;

public class BlockMetaPair
{
    public int id;
    public int data;

    public BlockMetaPair(int var1, int var2)
    {
        this.id = var1;
        this.data = var2;

        if (this.id >= MCVersionProxy.GetBlockIDCount())
        {
            this.id = 0;
        }
    }

    public int hashCode()
    {
        return (this.data << 16) + this.id;
    }

    public boolean equals(Object var1)
    {
        try
        {
            BlockMetaPair var2 = (BlockMetaPair)var1;
            return var2.id == this.id && var2.data == this.data;
        }
        catch (ClassCastException var3)
        {
            return false;
        }
    }

    public static BlockMetaPair parse(String var0)
    {
        String[] var1 = var0.split(":");

        if (var1.length != 2)
        {
            throw new NumberFormatException("Not a valid block ID/data value: " + var0);
        }
        else
        {
            return new BlockMetaPair(Integer.parseInt(var1[0]), Integer.parseInt(var1[1]));
        }
    }
}
