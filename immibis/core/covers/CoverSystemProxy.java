package immibis.core.covers;

import immibis.core.BlockMetaPair;
import immibis.core.Config;
import immibis.core.CoreProxy;
import immibis.core.IBlockIDCallback;
import immibis.core.MCVersionProxy;
import immibis.core.NonSharedProxy;
import immibis.core.covers.recipes.RecipeHollowCover;
import immibis.core.covers.recipes.RecipeHorizontalCut;
import immibis.core.covers.recipes.RecipeUnHollowCover;
import immibis.core.covers.recipes.RecipeVerticalCut;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.server.Block;
import net.minecraft.server.CraftingManager;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.LocaleLanguage;
import net.minecraft.server.Material;
import net.minecraft.server.mod_ImmibisCore;

public class CoverSystemProxy
{
    static int coverModel;
    public static BlockMultipart blockMultipart;
    public static ItemSaw itemSaw;
    public static final HashMap parts = new HashMap();
    public static ArrayList neiDamageValues = new ArrayList();
    public static int neiMaxDamage = 0;
    private static CoverSystemProxy.Info[] blockparts = new CoverSystemProxy.Info[] {new CoverSystemProxy.Info(EnumPartClass.Panel, 0.125D, "", " Cover"), new CoverSystemProxy.Info(EnumPartClass.Panel, 0.25D, "", " Panel"), new CoverSystemProxy.Info(EnumPartClass.Panel, 0.375D, "", " Triple Cover"), new CoverSystemProxy.Info(EnumPartClass.Panel, 0.5D, "", " Slab"), new CoverSystemProxy.Info(EnumPartClass.Panel, 0.625D, "", " Cover Slab"), new CoverSystemProxy.Info(EnumPartClass.Panel, 0.75D, "", " Triple Panel"), new CoverSystemProxy.Info(EnumPartClass.Panel, 0.875D, "", " Anticover"), null, new CoverSystemProxy.Info(EnumPartClass.Strip, 0.125D, "", " Cover Strip"), new CoverSystemProxy.Info(EnumPartClass.Strip, 0.25D, "", " Panel Strip"), new CoverSystemProxy.Info(EnumPartClass.Strip, 0.375D, "", " Triple Cover Strip"), new CoverSystemProxy.Info(EnumPartClass.Strip, 0.5D, "", " Slab Strip"), new CoverSystemProxy.Info(EnumPartClass.Strip, 0.625D, "", " Cover Slab Strip"), new CoverSystemProxy.Info(EnumPartClass.Strip, 0.75D, "", " Triple Panel Strip"), new CoverSystemProxy.Info(EnumPartClass.Strip, 0.875D, "", " Anticover Strip"), null, new CoverSystemProxy.Info(EnumPartClass.Corner, 0.125D, "", " Cover Corner"), new CoverSystemProxy.Info(EnumPartClass.Corner, 0.25D, "", " Panel Corner"), new CoverSystemProxy.Info(EnumPartClass.Corner, 0.375D, "", " Triple Cover Corner"), new CoverSystemProxy.Info(EnumPartClass.Corner, 0.5D, "", " Slab Corner"), new CoverSystemProxy.Info(EnumPartClass.Corner, 0.625D, "", " Cover Slab Corner"), new CoverSystemProxy.Info(EnumPartClass.Corner, 0.75D, "", " Triple Panel Corner"), new CoverSystemProxy.Info(EnumPartClass.Corner, 0.875D, "", " Anticover Corner"), null, new CoverSystemProxy.Info(EnumPartClass.HollowPanel, 0.125D, "Hollow ", " Cover"), new CoverSystemProxy.Info(EnumPartClass.HollowPanel, 0.25D, "Hollow ", " Panel"), new CoverSystemProxy.Info(EnumPartClass.HollowPanel, 0.375D, "Hollow ", " Triple Cover"), new CoverSystemProxy.Info(EnumPartClass.HollowPanel, 0.5D, "Hollow ", " Slab"), new CoverSystemProxy.Info(EnumPartClass.HollowPanel, 0.625D, "Hollow ", " Cover Slab"), new CoverSystemProxy.Info(EnumPartClass.HollowPanel, 0.75D, "Hollow ", " Triple Panel"), new CoverSystemProxy.Info(EnumPartClass.HollowPanel, 0.875D, "Hollow ", " Anticover"), null};

    public static void ModsLoaded() {}

    public static void init(int var0)
    {
        coverModel = var0;
        CoreProxy.RegisterBlockID("blockMultipart", new IBlockIDCallback()
        {
            public void registerBlock(int var1)
            {
                if (var1 == 0)
                {
                    mod_ImmibisCore.coversEnabled = false;
                }
                else
                {
                    CoverSystemProxy.blockMultipart = new BlockMultipart(var1, Material.STONE, CoverSystemProxy.coverModel);
                    CoverSystemProxy.itemSaw = new ItemSaw(Config.getInt("itemSaw.id", 22123));
                    CoverSystemProxy.blockMultipart.a("immibis.multipart");
                    NonSharedProxy.AddLocalization("immibis.multipart.name", "Multipart Block");
                    MCVersionProxy.ML_RegisterBlock(CoverSystemProxy.blockMultipart);
                    MCVersionProxy.ML_RegisterBlock(CoverSystemProxy.blockMultipart, ItemMultipart.class);
                    CoversNonSharedProxy.RegisterHighlightHandler();
                    MCVersionProxy.ML_RegisterTileEntity(TileMultipart.class, "immibis.core.multipart");
                    List var2 = CraftingManager.getInstance().getRecipies();
                    var2.add(new RecipeHollowCover());
                    var2.add(new RecipeUnHollowCover());
                    var2.add(new RecipeVerticalCut());
                    CoverSystemProxy.RegisterPartsBasedOnBlock(0, Block.BEDROCK);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(1, Block.CLAY);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(2, Block.DIAMOND_BLOCK);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(3, Block.GOLD_BLOCK);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(4, Block.LAPIS_BLOCK);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(5, Block.SNOW_BLOCK);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(6, Block.IRON_BLOCK);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(7, Block.BOOKSHELF);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(8, Block.BRICK);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(9, Block.COBBLESTONE);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(10, Block.MOSSY_COBBLESTONE);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(11, Block.DIRT);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(12, Block.GLASS);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(13, Block.GRAVEL);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(14, Block.ICE);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(15, Block.MELON);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(16, Block.OBSIDIAN);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(17, Block.COAL_ORE);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(18, Block.DIAMOND_ORE);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(19, Block.GOLD_ORE);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(20, Block.IRON_ORE);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(21, Block.LAPIS_ORE);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(22, Block.REDSTONE_ORE);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(23, Block.WOOD);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(24, Block.PUMPKIN);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(25, Block.SAND);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(26, Block.SANDSTONE);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(27, Block.SPONGE);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(28, Block.STONE);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(29, Block.TNT);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(30, Block.LOG);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(31, Block.LOG, 1);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(32, Block.LOG, 2);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(33, Block.WORKBENCH);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(34, Block.WOOL, 0);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(35, Block.WOOL, 1);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(36, Block.WOOL, 2);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(37, Block.WOOL, 3);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(38, Block.WOOL, 4);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(39, Block.WOOL, 5);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(40, Block.WOOL, 6);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(41, Block.WOOL, 7);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(42, Block.WOOL, 8);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(43, Block.WOOL, 9);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(44, Block.WOOL, 10);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(45, Block.WOOL, 11);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(46, Block.WOOL, 12);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(47, Block.WOOL, 13);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(48, Block.WOOL, 14);
                    CoverSystemProxy.RegisterPartsBasedOnBlock(49, Block.WOOL, 15);
                    MCVersionProxy.ML_AddRecipe(new ItemStack(CoverSystemProxy.itemSaw), new Object[] {"III", "DDI", 'I', Item.IRON_INGOT, 'D', Item.DIAMOND});
                }
            }
        });
    }

    private static void RegisterPartsBasedOnBlock(int var0, Block var1)
    {
        RegisterPartsBasedOnBlock(var0, var1, 0);
    }

    private static void RegisterPartsBasedOnBlock(int var0, Block var1, int var2)
    {
        assert blockparts.length == 32;
        String var3 = LocaleLanguage.a().b(Item.byId[var1.id].a(new ItemStack(var1.id, 1, var2)) + ".name");
        float var4 = var1.getHardness(var2);
        int[] var5 = new int[6];
        int var6;

        for (var6 = 0; var6 < 6; ++var6)
        {
            var5[var6] = var1.a(var6, var2);
        }

        for (var6 = 0; var6 < 7; ++var6)
        {
            RecipeHollowCover.addMap(var0 * 32 + var6, var0 * 32 + var6 + 24);
            RecipeUnHollowCover.addMap(var0 * 32 + var6 + 24, var0 * 32 + var6);
            RecipeHorizontalCut.addMap(new BlockMetaPair(var0 * 32 + var6, blockMultipart.id), new ItemStack(blockMultipart.id, 2, var0 * 32 + var6 + 8));
            RecipeHorizontalCut.addMap(new BlockMetaPair(var0 * 32 + var6 + 8, blockMultipart.id), new ItemStack(blockMultipart.id, 2, var0 * 32 + var6 + 16));
        }

        RecipeVerticalCut.addMap(new BlockMetaPair(var1.id, var2), new ItemStack(blockMultipart.id, 2, var0 * 32 + 3));
        RecipeVerticalCut.addMap(new BlockMetaPair(blockMultipart.id, var0 * 32 + 3), new ItemStack(blockMultipart.id, 2, var0 * 32 + 1));
        RecipeVerticalCut.addMap(new BlockMetaPair(blockMultipart.id, var0 * 32 + 1), new ItemStack(blockMultipart.id, 2, var0 * 32 + 0));
        RecipeVerticalCut.addMap(new BlockMetaPair(blockMultipart.id, var0 * 32 + 27), new ItemStack(blockMultipart.id, 2, var0 * 32 + 25));
        RecipeVerticalCut.addMap(new BlockMetaPair(blockMultipart.id, var0 * 32 + 25), new ItemStack(blockMultipart.id, 2, var0 * 32 + 24));

        for (var6 = 0; var6 < blockparts.length; ++var6)
        {
            if (blockparts[var6] != null)
            {
                PartType var7 = new PartType(blockparts[var6].clazz, blockparts[var6].size, blockparts[var6].prefix + var3 + blockparts[var6].suffix, var4, var1, var2);
                var7.textures = var5;
                RegisterPartType(var0 * 32 + var6, var7);
            }
        }
    }

    public static void RegisterPartType(int var0, PartType var1)
    {
        if (parts.containsKey(Integer.valueOf(var0)))
        {
            throw new PartIDInUseException(var0, (PartType)parts.get(Integer.valueOf(var0)), var1);
        }
        else
        {
            parts.put(Integer.valueOf(var0), var1);
            neiDamageValues.add(Integer.valueOf(var0));

            if (var0 >= neiMaxDamage)
            {
                neiMaxDamage = var0 + 1;
            }

            var1.id = var0;
            NonSharedProxy.AddLocalization("immibis.core.multipart." + var0 + ".name", var1.name);
        }
    }

    private static class Info
    {
        public EnumPartClass clazz;
        public double size;
        public String prefix;
        public String suffix;

        public Info(EnumPartClass var1, double var2, String var4, String var5)
        {
            this.clazz = var1;
            this.size = var2;
            this.prefix = var4;
            this.suffix = var5;
        }
    }
}
