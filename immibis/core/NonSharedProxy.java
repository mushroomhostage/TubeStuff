package immibis.core;

import java.io.File;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet3Chat;

public class NonSharedProxy
{
    public static File getMinecraftDir()
    {
        return new File(".");
    }

    public static void AddLocalization(String var0, String var1) {}

    public static void sendChat(EntityHuman var0, String var1)
    {
        if (var0 instanceof EntityPlayer)
        {
            ((EntityPlayer)var0).netServerHandler.networkManager.queue(new Packet3Chat(var1));
        }
    }
}
