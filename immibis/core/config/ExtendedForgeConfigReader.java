package immibis.core.config;

import immibis.core.NonSharedProxy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class ExtendedForgeConfigReader implements IConfigReader
{
    private HashMap keys = new HashMap();

    public ExtendedForgeConfigReader(String var1) throws IOException
    {
        File var2 = new File(NonSharedProxy.getMinecraftDir(), var1);
        BufferedReader var3 = new BufferedReader(new FileReader(var2));

        try
        {
            Stack var4 = new Stack();
            String var5 = "";

            while (true)
            {
                String var6 = var3.readLine();

                if (var6 == null)
                {
                    return;
                }

                var6 = var6.trim();

                if (!var6.startsWith("#"))
                {
                    if (var6.endsWith("{"))
                    {
                        var4.push(var5);
                        String var7 = var6.substring(0, var6.indexOf("{")).trim();
                        var5 = var5.equals("") ? var7 : var5 + "." + var7;
                    }
                    else if (var6.indexOf("}") >= 0)
                    {
                        var5 = (String)var4.pop();
                    }
                    else
                    {
                        int var13 = var6.indexOf("=");

                        if (var13 > 0)
                        {
                            String var8 = var6.substring(0, var13).trim();
                            String var9 = var6.substring(var13 + 1).trim();
                            var8 = var5.equals("") ? var8 : var5 + "." + var8;
                            this.keys.put(var8, var9);
                        }
                    }
                }
            }
        }
        finally
        {
            var3.close();
        }
    }

    public String getConfigEntry(String var1)
    {
        return (String)this.keys.get(var1);
    }
}
