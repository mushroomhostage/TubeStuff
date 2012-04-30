package immibis.tubestuff;

import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.InventoryCrafting;

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
