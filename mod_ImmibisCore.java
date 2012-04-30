package net.minecraft.src;

import immibis.core.CompatibleBaseMod;
import immibis.core.CoreProxy;
import immibis.core.ModInfoReader;
import immibis.core.Packet230ModLoader;
import immibis.core.covers.BlockMultipart;
import immibis.core.covers.CoverSystemProxy;
import immibis.core.covers.EnumPosition;
import immibis.core.covers.ItemMultipart;

public class mod_ImmibisCore extends CompatibleBaseMod
{
    public static boolean coversEnabled = true;
    private boolean loaded = false;
    public static mod_ImmibisCore instance;

    public String getVersion()
    {
        return ModInfoReader.getModInfoField("/immibis/mod_ImmibisCore.info.txt", "version");
    }

    public void load()
    {
        if (!this.loaded)
        {
            this.loaded = true;
            coversEnabled = false;
            CoreProxy.load();
        }
    }

    public void modsLoaded()
    {
        CoreProxy.AllocateBlockIDs();
        CoverSystemProxy.ModsLoaded();
    }

    public mod_ImmibisCore()
    {
        instance = this;
    }

    public void handlePacket(Packet230ModLoader var1, EntityPlayer var2)
    {
        if (var1.packetType == 0)
        {
            BlockMultipart.setPunchingSubhit(var2, var1.dataInt[3]);
        }
        else if (var1.packetType == 1)
        {
            if (var1.dataInt.length != 4)
            {
                return;
            }

            int var3 = var1.dataInt[0];
            int var4 = var1.dataInt[1];
            int var5 = var1.dataInt[2];
            int var6 = var1.dataInt[3];

            if (var6 < 0 || var6 >= EnumPosition.values().length)
            {
                return;
            }

            EnumPosition var7 = EnumPosition.values()[var6];
            ItemStack var8 = var2.U();

            if (var8 == null || !(Item.byId[var8.id] instanceof ItemMultipart))
            {
                return;
            }

            ItemMultipart var9 = (ItemMultipart)Item.byId[var8.id];
            var9.placeInBlock(var2.world, var3, var4, var5, var7, var8);
            var2.world.notify(var3, var4, var5);

            if (var8.count == 0)
            {
                var2.V();
            }
        }
    }
}
