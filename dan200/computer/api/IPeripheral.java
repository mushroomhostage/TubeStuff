package dan200.computer.api;

public interface IPeripheral
{
    String getType();

    String[] getMethodNames();

    Object[] callMethod(IComputerAccess var1, int var2, Object[] var3) throws Exception;

    boolean canAttachToSide(int var1);

    void attach(IComputerAccess var1, String var2);

    void detach(IComputerAccess var1);
}
