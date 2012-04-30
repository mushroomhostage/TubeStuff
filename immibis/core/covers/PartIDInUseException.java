package immibis.core.covers;

public class PartIDInUseException extends RuntimeException
{
    public PartIDInUseException(int var1, PartType var2, PartType var3)
    {
        super("Micropart ID " + var1 + " is already used by " + var2 + " when adding " + var3);
    }
}
