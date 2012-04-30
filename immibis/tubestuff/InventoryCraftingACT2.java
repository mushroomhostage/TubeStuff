package immibis.tubestuff;

import net.minecraft.src.Container;
import net.minecraft.src.EntityHuman;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryCrafting;

public class InventoryCraftingACT2 extends InventoryCrafting
{
    public InventoryCraftingACT2()
    {
        super(new Container()
        {
            public boolean b(EntityHuman var1)
            {
                return false;
            }
            public void a(IInventory var1) {}
        }, 3, 3);
    }
}
