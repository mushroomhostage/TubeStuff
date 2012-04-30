package net.minecraft.src;

import immibis.core.CompatibleBaseMod;
import immibis.core.CoreProxy;
import immibis.core.IBlockIDCallback;
import immibis.core.MCVersionProxy;
import immibis.core.ModInfoReader;
import immibis.core.sprites.ModSpriteSheet;
import immibis.tubestuff.BlockTubestuff;
import immibis.tubestuff.ItemTubestuff;
import immibis.tubestuff.SharedProxy;
import immibis.tubestuff.TileBuffer;
import immibis.tubestuff.TileCraftingTable;
import immibis.tubestuff.TileInfiniteChest;

public class mod_TubeStuff extends CompatibleBaseMod
{
    public static mod_TubeStuff instance;
    public static ModSpriteSheet sprites;
    public static BlockTubestuff block;
    private boolean loaded = false;

    public mod_TubeStuff()
    {
        instance = this;
    }

    public String getVersion()
    {
        return ModInfoReader.getModInfoField("/immibis/mod_TubeStuff.info.txt", "version");
    }

    public void load()
    {
        if (!this.loaded)
        {
            this.loaded = true;
            sprites = new ModSpriteSheet("/immibis/tubestuff.png", 5, 1, new int[] {0, 1, 2, 3, 4});
            CoreProxy.RegisterBlockID("tubestuff", new IBlockIDCallback()
            {
                public void registerBlock(int var1)
                {
                    mod_TubeStuff.block = new BlockTubestuff(var1);
                    MCVersionProxy.ML_RegisterBlock(mod_TubeStuff.block, ItemTubestuff.class);
                    MCVersionProxy.ML_RegisterTileEntity(TileBuffer.class, "TubeStuff buffer");
                    MCVersionProxy.ML_RegisterTileEntity(TileCraftingTable.class, "TubeStuff crafting table");
                    MCVersionProxy.ML_RegisterTileEntity(TileInfiniteChest.class, "TubeStuff infinite chest");
                }
            });
        }
    }

    public void modsLoaded()
    {
        this.load();
        SharedProxy.ModsLoaded();
    }
}
