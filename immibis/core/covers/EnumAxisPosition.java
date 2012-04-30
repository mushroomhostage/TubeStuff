package immibis.core.covers;

public enum EnumAxisPosition
{
    Negative,
    Positive,
    Centre,
    Span;

    public boolean touchesNegative()
    {
        return this == Negative || this == Span;
    }

    public boolean touchesPositive()
    {
        return this == Positive || this == Span;
    }
}
