package immibis.core;

import net.minecraft.src.Container;
import net.minecraft.src.EntityHuman;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_ImmibisCore;

public class GuiSystem
{
    private static boolean force = false;

    public static void OpenStringGUI(Class var0, String var1, EntityHuman var2, Container var3, TileEntity var4)
    {
        if (var2 instanceof EntityPlayer)
        {
            EntityPlayer var5 = (EntityPlayer)var2;
            var5.realGetNextWidowId();
            int var6 = var5.getCurrentWindowIdField();
            var5.H();
            Packet230ModLoader var7 = new Packet230ModLoader();
            var7.packetType = 0;
            var7.dataString = new String[] {var0.getName(), var1, var4.getClass().getName()};
            var7.dataInt = new int[] {var6, var4.x, var4.y, var4.z};
            mod_ImmibisCore.instance.sendPacket(var7, var5);
            var2.activeContainer = var3;
            var2.activeContainer.windowId = var6;
            var2.activeContainer.addSlotListener(var5);
        }
    }
}
