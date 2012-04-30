package immibis.core.config;

import immibis.core.NonSharedProxy;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyConfigReader implements IConfigReader
{
    private Properties props;

    public PropertyConfigReader(String var1) throws IOException
    {
        File var2 = new File(NonSharedProxy.getMinecraftDir(), var1);
        this.props = new Properties();
        FileInputStream var3 = new FileInputStream(var2);

        try
        {
            this.props.load(var3);
        }
        finally
        {
            var3.close();
        }
    }

    public String getConfigEntry(String var1)
    {
        return this.props.getProperty(var1);
    }
}
