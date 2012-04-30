package immibis.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet250CustomPayload;

public class Packet230ModLoader
{
    public int packetType;
    public String[] dataString;
    public int[] dataInt;

    public void writeData(DataOutputStream var1) throws IOException
    {
        var1.writeInt(this.packetType);
        var1.writeInt(this.dataString.length);
        var1.writeInt(this.dataInt.length);
        String[] var2 = this.dataString;
        int var3 = var2.length;
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            String var5 = var2[var4];
            var1.writeUTF(var5);
        }

        int[] var6 = this.dataInt;
        var3 = var6.length;

        for (var4 = 0; var4 < var3; ++var4)
        {
            int var7 = var6[var4];
            var1.writeInt(var7);
        }
    }

    public void readData(DataInputStream var1) throws IOException
    {
        this.packetType = var1.readInt();
        int var2 = var1.readInt();
        int var3 = var1.readInt();

        if (var2 <= 32 && var3 <= 32 && var2 >= 0 && var3 >= 0)
        {
            int var4;

            if (var2 == 0)
            {
                this.dataString = null;
            }
            else
            {
                this.dataString = new String[var2];

                for (var4 = 0; var4 < var2; ++var4)
                {
                    this.dataString[var4] = var1.readUTF();
                }
            }

            if (var3 == 0)
            {
                this.dataInt = null;
            }
            else
            {
                this.dataInt = new int[var3];

                for (var4 = 0; var4 < var3; ++var4)
                {
                    this.dataInt[var4] = var1.readInt();
                }
            }
        }
        else
        {
            throw new IOException("");
        }
    }

    public Packet getPacket(String var1)
    {
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        DataOutputStream var3 = new DataOutputStream(var2);
        Packet250CustomPayload var4 = new Packet250CustomPayload();

        try
        {
            this.writeData(var3);
        }
        catch (IOException var6)
        {
            var6.printStackTrace();
        }

        var4.tag = var1;
        var4.data = var2.toByteArray();
        var4.length = var4.data.length;
        return var4;
    }

    public static Packet230ModLoader read(byte[] var0) throws IOException
    {
        DataInputStream var1 = new DataInputStream(new ByteArrayInputStream(var0));
        Packet230ModLoader var2 = new Packet230ModLoader();
        var2.readData(var1);
        return var2;
    }
}
