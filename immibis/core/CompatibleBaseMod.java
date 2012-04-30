package immibis.core;

import forge.IConnectionHandler;
import forge.IPacketHandler;
import forge.MessageManager;
import forge.MinecraftForge;
import forge.NetworkMod;
import java.io.IOException;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet1Login;

public abstract class CompatibleBaseMod extends NetworkMod
{
    private String fake230channel = null;

    public void handlePacket(Packet230ModLoader var1, EntityPlayer var2) {}

    public void sendPacket(Packet230ModLoader var1, EntityHuman var2)
    {
        if (var2 instanceof EntityPlayer)
        {
            ((EntityPlayer)var2).netServerHandler.networkManager.queue(var1.getPacket(this.fake230channel));
        }
    }

    public void initFake230(final String var1)
    {
        this.fake230channel = var1;
        MinecraftForge.registerConnectionHandler(new IConnectionHandler()
        {
            public void onLogin(NetworkManager var1x, Packet1Login var2)
            {
                MessageManager.getInstance().registerChannel(var1x, new IPacketHandler()
                {
                    public void onPacketData(NetworkManager var1x, String var2, byte[] var3)
                    {
                        if (var2.equals(var1))
                        {
                            try
                            {
                                CompatibleBaseMod.this.handlePacket(Packet230ModLoader.read(var3), ((NetServerHandler)var1x.getNetHandler()).getPlayerEntity());
                            }
                            catch (IOException var5)
                            {
                                var5.printStackTrace();
                            }
                        }
                    }
                }, var1);
            }
            public void onDisconnect(NetworkManager var1x, String var2, Object[] var3) {}
            public void onConnect(NetworkManager var1x) {}
        });
    }

    public boolean clientSideRequired()
    {
        return true;
    }

    public boolean serverSideRequired()
    {
        return false;
    }
}
