package immibis.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.server.mod_ImmibisCore;

public class CoreProxy
{
    public static final int PACKET_TYPE_S2C_STRING_GUI = 0;
    public static final int PACKET_TYPE_S2C_MULTIPART_DESCRIPTION = 1;
    public static final int PACKET_TYPE_C2S_MICROBLOCK_DIG_START = 0;
    public static final int PACKET_TYPE_C2S_MICROBLOCK_PLACE = 1;
    public static final boolean FORCE_MICROBLOCKS_DISABLED = true;
    private static HashMap block_id_callbacks = new HashMap();

    public static void RegisterBlockID(String var0, IBlockIDCallback var1)
    {
        int var2 = Config.getBlockID(var0, false);

        if (var2 != 0)
        {
            var1.registerBlock(var2);
        }
        else
        {
            block_id_callbacks.put(var0, var1);
        }
    }

    public static void load()
    {
        mod_ImmibisCore.instance.initFake230("ImmibisCore230");
    }

    public static void AllocateBlockIDs()
    {
        Config.save();
        Iterator var0 = block_id_callbacks.entrySet().iterator();

        while (var0.hasNext())
        {
            Entry var1 = (Entry)var0.next();
            ((IBlockIDCallback)var1.getValue()).registerBlock(Config.getBlockID((String)var1.getKey(), true));
        }

        block_id_callbacks.clear();
        Config.save();
    }
}
