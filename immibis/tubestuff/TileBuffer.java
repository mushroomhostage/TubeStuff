package immibis.tubestuff;

import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IPeripheral;
import immibis.core.GuiSystem;
import immibis.core.TileBasicInventory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import net.minecraft.src.ContainerChest;
import net.minecraft.src.EntityHuman;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class TileBuffer extends TileBasicInventory implements IPeripheral
{
    public static final int INVSIZE = 18;
    private transient LinkedList notify = new LinkedList();
    private boolean override = false;
    private int overrideEmitSlot = -1;
    private IComputerAccess overrideEmitPing;
    private static int overrideEmitNextToken = 0;
    private int overrideEmitToken;
    private int update_ticks = 0;
    private boolean inv_empty = false;
    private boolean find_pipe = true;
    private TileEntity out_pipe = null;
    private int delay = 20;
    private static final int MIN_DELAY = 10;
    private static final int MAX_DELAY = 100;
    private static Class class_PipeTransportItems = null;
    private static Class class_EntityPassiveItem = null;
    private static Class enum_Orientations = null;
    private static Class class_TileGenericPipe = null;
    private static Class class_Pipe = null;
    private static Constructor cons_EntityPassiveItem = null;
    private static Method meth_entityEntering = null;
    private static Field field_transport = null;
    private static Field field_pipe = null;

    public TileBuffer()
    {
        super(18, "Buffer");
    }

    private Object getBCDir(String var1)
    {
        try
        {
            return enum_Orientations.getField(var1).get((Object)null);
        }
        catch (Exception var3)
        {
            return null;
        }
    }

    private boolean enterPipe(ItemStack var1)
    {
        if (this.out_pipe == null)
        {
            return true;
        }
        else
        {
            try
            {
                Object var2 = null;

                if (this.out_pipe.x < this.x)
                {
                    var2 = this.getBCDir("XNeg");
                }
                else if (this.out_pipe.x > this.x)
                {
                    var2 = this.getBCDir("XPos");
                }
                else if (this.out_pipe.y < this.y)
                {
                    var2 = this.getBCDir("YNeg");
                }
                else if (this.out_pipe.y > this.y)
                {
                    var2 = this.getBCDir("YPos");
                }
                else if (this.out_pipe.z < this.z)
                {
                    var2 = this.getBCDir("ZNeg");
                }
                else if (this.out_pipe.z > this.z)
                {
                    var2 = this.getBCDir("ZPos");
                }

                Object var3 = cons_EntityPassiveItem.newInstance(new Object[] {this.world, Double.valueOf((double)(this.x + this.out_pipe.x + 1) / 2.0D), Double.valueOf((double)(this.y + this.out_pipe.y) / 2.0D), Double.valueOf((double)(this.z + this.out_pipe.z + 1) / 2.0D), var1});
                meth_entityEntering.invoke(field_transport.get(field_pipe.get(this.out_pipe)), new Object[] {var3, var2});
                return true;
            }
            catch (Exception var4)
            {
                var4.printStackTrace();
                this.out_pipe = null;
                return false;
            }
        }
    }

    private void checkPipe(TileEntity var1)
    {
        if (var1 != null && this.out_pipe == null && var1.getClass().getName().equals("buildcraft.transport.TileGenericPipe"))
        {
            try
            {
                String var2 = field_pipe.get(var1).getClass().getName();

                if (var2.equals("buildcraft.transport.pipes.PipeItemsWood") || var2.equals("buildcraft.transport.pipes.PipeItemsGold") || var2.equals("buildcraft.krapht.pipes.PipeItemsBasicLogistics"))
                {
                    this.out_pipe = var1;
                }
            }
            catch (Exception var3)
            {
                ;
            }
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void q_()
    {
        if (!this.inv_empty && !this.world.isStatic)
        {
            if (++this.update_ticks < this.delay)
            {
                if (this.update_ticks > 2 && this.redstone_output)
                {
                    this.redstone_output = false;
                    this.notifyNeighbouringBlocks();

                    if (this.override && this.overrideEmitPing != null)
                    {
                        this.overrideEmitPing.queueEvent("immibis.tubestuff.buffer.emitStackDone", new Object[] {Integer.valueOf(this.overrideEmitToken)});
                        this.overrideEmitPing = null;
                    }
                }
            }
            else
            {
                this.update_ticks = 0;
                int var1 = 0;
                ItemStack[] var2 = this.inv.contents;

                if (this.override)
                {
                    if (this.overrideEmitSlot == -1)
                    {
                        return;
                    }

                    var1 = this.overrideEmitSlot;
                    this.overrideEmitSlot = -1;
                }
                else
                {
                    int var3 = 0;
                    int var4 = 0;
                    int var5 = 64;
                    int var6 = 0;

                    for (int var7 = 0; var7 < 18; ++var7)
                    {
                        if (var2[var7] != null)
                        {
                            ++var6;

                            if (var2[var7].count > var3)
                            {
                                var3 = var2[var7].count;
                                var1 = var7;
                            }

                            if (var2[var7].count < var5)
                            {
                                var5 = var2[var7].count;
                                var4 = var7;
                            }
                        }
                    }

                    if (var3 == 0 || var6 == 0)
                    {
                        this.inv_empty = true;
                        return;
                    }

                    this.delay = 100 + (int)(-90.0D * ((double)var6 / 18.0D));

                    if (var3 < var2[var1].getMaxStackSize() / 2)
                    {
                        var1 = var4;
                    }
                }

                if (var1 != 0)
                {
                    ItemStack var8 = var2[var1];
                    var2[var1] = var2[0];
                    var2[0] = var8;
                    this.update();
                    Iterator var9 = this.notify.iterator();

                    while (var9.hasNext())
                    {
                        IComputerAccess var10 = (IComputerAccess)var9.next();
                        var10.queueEvent("immibis.tubestuff.buffer.notify", new Object[] {Integer.valueOf(var2[0].id), Integer.valueOf(var2[0].count), Integer.valueOf(var2[0].getData())});
                    }
                }

                if (this.find_pipe)
                {
                    this.out_pipe = null;
                    this.checkPipe(this.world.getTileEntity(this.x - 1, this.y, this.z));
                    this.checkPipe(this.world.getTileEntity(this.x + 1, this.y, this.z));
                    this.checkPipe(this.world.getTileEntity(this.x, this.y - 1, this.z));
                    this.checkPipe(this.world.getTileEntity(this.x, this.y + 1, this.z));
                    this.checkPipe(this.world.getTileEntity(this.x, this.y, this.z - 1));
                    this.checkPipe(this.world.getTileEntity(this.x, this.y, this.z + 1));
                    this.find_pipe = false;
                }

                if (this.out_pipe != null)
                {
                    if (var2[0] != null)
                    {
                        if (this.enterPipe(var2[0]))
                        {
                            var2[0] = null;
                            this.update();
                        }
                        else
                        {
                            this.out_pipe = null;
                        }
                    }
                }
                else if (!this.redstone_output)
                {
                    this.redstone_output = true;
                    this.notifyNeighbouringBlocks();
                }
            }
        }
    }

    public void onBlockNeighbourChange()
    {
        this.find_pipe = true;
    }

    public boolean onBlockActivated(EntityHuman var1)
    {
        GuiSystem.OpenStringGUI(TileBuffer.class, "gui", var1, new ContainerChest(var1.inventory, this), this);
        return true;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int var1, ItemStack var2)
    {
        super.setItem(var1, var2);
        this.inv_empty = false;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound var1)
    {
        super.b(var1);
        var1.setBoolean("override", this.override);
    }

    public String[] getMethodNames()
    {
        return new String[] {"getItemID", "getQuantity", "getDamage", "moveItems", "emitStack", "setOverride", "getOverride", "setNotify", "getNotify"};
    }

    public Object[] callMethod(IComputerAccess var1, int var2, Object[] var3) throws Exception
    {
        int var9;

        switch (var2)
        {
            case 0:
            case 1:
            case 2:
                if (!SharedProxy.CC_CheckArgs(var3, new Class[] {Double.class}))
                {
                    return new Object[] {Boolean.valueOf(false), "Wrong arguments"};
                }
                else
                {
                    var9 = (int)((Double)var3[0]).doubleValue();

                    if (var9 >= 0 && var9 < this.getSize())
                    {
                        ItemStack var10 = this.getItem(var9);

                        if (var10 == null)
                        {
                            return new Object[] {Boolean.valueOf(true), Integer.valueOf(0)};
                        }

                        return new Object[] {Boolean.valueOf(true), Integer.valueOf(var2 == 0 ? var10.id : (var2 == 1 ? var10.count : var10.getData()))};
                    }

                    return new Object[] {Boolean.valueOf(false), "Slot number out of range"};
                }

            case 3:
                if (!this.override)
                {
                    return new Object[] {Boolean.valueOf(false), "Not in override mode"};
                }
                else if (!SharedProxy.CC_CheckArgs(var3, new Class[] {Double.class, Double.class, Double.class}))
                {
                    return new Object[] {Boolean.valueOf(false), "Wrong arguments"};
                }
                else
                {
                    var9 = (int)((Double)var3[0]).doubleValue();
                    int var5 = (int)((Double)var3[1]).doubleValue();
                    int var6 = (int)((Double)var3[2]).doubleValue();

                    if (var9 >= 0 && var5 >= 0 && var9 < this.getSize() && var5 < this.getSize())
                    {
                        if (var9 == var5)
                        {
                            return new Object[] {Boolean.valueOf(false), "Source and destination are the same"};
                        }
                        else
                        {
                            ItemStack var7 = this.getItem(var9);
                            ItemStack var8 = this.getItem(var5);
                            return var7 != null && var7.count >= var6 ? (var8 != null && (var8.id != var7.id || var8.getData() != var7.getData()) ? new Object[] {Boolean.valueOf(false), "Cannot stack different items"}: (var8.count + var6 > var8.getMaxStackSize() ? new Object[] {Boolean.valueOf(false), "Would exceed maximum stack size"}: new Object[] {Boolean.valueOf(true)})): new Object[] {Boolean.valueOf(false), "Insufficient items in source slot"};
                        }
                    }
                    else
                    {
                        return new Object[] {Boolean.valueOf(false), "Slot number out of range"};
                    }
                }

            case 4:
                if (!this.override)
                {
                    return new Object[] {Boolean.valueOf(false), "Not in override mode"};
                }
                else if (!SharedProxy.CC_CheckArgs(var3, new Class[] {Double.class}))
                {
                    return new Object[] {Boolean.valueOf(false), "Wrong arguments"};
                }
                else
                {
                    var9 = (int)((Double)var3[0]).doubleValue();

                    if (this.overrideEmitSlot != -1)
                    {
                        return new Object[] {Boolean.valueOf(false), "busy"};
                    }

                    this.overrideEmitToken = overrideEmitNextToken++;
                    this.overrideEmitPing = var1;
                    this.overrideEmitSlot = var9;
                    this.delay = 20;
                    return new Object[] {Boolean.valueOf(true), Integer.valueOf(this.overrideEmitToken)};
                }

            case 5:
            case 7:
                if (!SharedProxy.CC_CheckArgs(var3, new Class[] {Boolean.class}))
                {
                    return new Object[] {Boolean.valueOf(false), "Wrong arguments"};
                }
                boolean var4 = ((Boolean)var3[0]).booleanValue();

                if (var2 == 5)
                {
                    this.override = var4;

                    if (!var4)
                    {
                        this.update_ticks = this.delay;
                    }
                }
                else if (var4)
                {
                    if (!this.notify.contains(var1))
                    {
                        this.notify.add(var1);
                    }
                }
                else
                {
                    this.notify.remove(var1);
                }

                return new Object[] {Boolean.valueOf(true)};

            case 6:
                return new Object[] {Boolean.valueOf(this.override)};
            case 8:
                return new Object[] {Boolean.valueOf(this.notify.contains(var1))};
            default:
                return new Object[] {Boolean.valueOf(false), "Invalid method index"};
        }
    }

    public String getType()
    {
        return "immibis.tubestuff.buffer";
    }

    public void attach(IComputerAccess var1, String var2) {}

    public void detach(IComputerAccess var1)
    {
        this.notify.remove(var1);

        if (this.notify.size() == 0)
        {
            this.override = false;
        }
    }

    public boolean canAttachToSide(int var1)
    {
        return true;
    }

    static
    {
        try
        {
            ClassLoader var0 = TileBuffer.class.getClassLoader();
            class_PipeTransportItems = var0.loadClass("buildcraft.transport.PipeTransportItems");
            class_Pipe = var0.loadClass("buildcraft.transport.Pipe");
            class_EntityPassiveItem = var0.loadClass("buildcraft.api.EntityPassiveItem");
            enum_Orientations = var0.loadClass("buildcraft.api.Orientations");
            class_TileGenericPipe = var0.loadClass("buildcraft.transport.TileGenericPipe");
            cons_EntityPassiveItem = class_EntityPassiveItem.getConstructor(new Class[] {World.class, Double.TYPE, Double.TYPE, Double.TYPE, ItemStack.class});
            meth_entityEntering = class_PipeTransportItems.getMethod("entityEntering", new Class[] {class_EntityPassiveItem, enum_Orientations});
            field_pipe = class_TileGenericPipe.getField("pipe");
            field_transport = class_Pipe.getField("transport");
        }
        catch (Exception var1)
        {
            ModLoader.getLogger().info("Could not access BuildCraft for buffer output because:");
            ModLoader.getLogger().info(var1.getClass().getName() + ": " + var1.getMessage());
            class_PipeTransportItems = null;
            class_Pipe = null;
            class_EntityPassiveItem = null;
            enum_Orientations = null;
            class_TileGenericPipe = null;
            cons_EntityPassiveItem = null;
            meth_entityEntering = null;
            field_pipe = null;
            field_transport = null;
        }
    }
}
