package immibis.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ModInfoReader
{
    private static Map files = new HashMap();

    public static String getModInfoField(String var0, String var1)
    {
        Properties var2 = (Properties)files.get(var0);

        if (var2 == null)
        {
            var2 = new Properties();
            InputStream var3 = null;

            try
            {
                var3 = ModInfoReader.class.getResourceAsStream(var0);
                var3.available();
            }
            catch (Exception var6)
            {
                throw new RuntimeException(var6);
            }

            try
            {
                var2.load(var3);
                var3.close();
            }
            catch (IOException var5)
            {
                throw new RuntimeException(var5);
            }

            files.put(var0, var2);
        }

        return var2.getProperty(var1);
    }
}
