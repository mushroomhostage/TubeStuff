package immibis.core;

import net.minecraft.server.ItemBlock;
import net.minecraft.server.ItemStack;

public class ItemCombined extends ItemBlock
{
    public BlockCombined block;
    private String[] names;

    public ItemCombined(int var1, String[] var2)
    {
        super(var1);
        this.setMaxDurability(0);
        this.a(true);
        this.names = new String[16];

        for (int var3 = 0; var3 < var2.length && var3 < 16; ++var3)
        {
            this.names[var3] = var1 + "." + var3;
        }
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int filterData(int var1)
    {
        return var1;
    }

    public String a(ItemStack var1)
    {
        int var2 = var1.getData();
        return var2 >= 0 && var2 < 16 && this.names[var2] != null ? this.names[var2] : "";
    }
}
