package dan200.computer.api;

public interface IComputerAccess
{
    int createNewSaveDir(String var1);

    String mountSaveDir(String var1, String var2, int var3, boolean var4);

    String mountFixedDir(String var1, String var2, boolean var3);

    void unmount(String var1);

    int getID();

    void queueEvent(String var1);

    void queueEvent(String var1, Object[] var2);
}
