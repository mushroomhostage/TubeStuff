package immibis.core.covers;

public enum EnumPosition
{
    Centre(EnumAxisPosition.Centre, EnumAxisPosition.Centre, EnumAxisPosition.Centre, EnumPositionClass.Centre),
    CoverNX(EnumAxisPosition.Negative, EnumAxisPosition.Span, EnumAxisPosition.Span, EnumPositionClass.Face),
    CoverPX(EnumAxisPosition.Positive, EnumAxisPosition.Span, EnumAxisPosition.Span, EnumPositionClass.Face),
    CoverNY(EnumAxisPosition.Span, EnumAxisPosition.Negative, EnumAxisPosition.Span, EnumPositionClass.Face),
    CoverPY(EnumAxisPosition.Span, EnumAxisPosition.Positive, EnumAxisPosition.Span, EnumPositionClass.Face),
    CoverNZ(EnumAxisPosition.Span, EnumAxisPosition.Span, EnumAxisPosition.Negative, EnumPositionClass.Face),
    CoverPZ(EnumAxisPosition.Span, EnumAxisPosition.Span, EnumAxisPosition.Positive, EnumPositionClass.Face),
    EdgeNXNY(EnumAxisPosition.Negative, EnumAxisPosition.Negative, EnumAxisPosition.Span, EnumPositionClass.Edge),
    EdgeNXPY(EnumAxisPosition.Negative, EnumAxisPosition.Positive, EnumAxisPosition.Span, EnumPositionClass.Edge),
    EdgePXNY(EnumAxisPosition.Positive, EnumAxisPosition.Negative, EnumAxisPosition.Span, EnumPositionClass.Edge),
    EdgePXPY(EnumAxisPosition.Positive, EnumAxisPosition.Positive, EnumAxisPosition.Span, EnumPositionClass.Edge),
    EdgeNXNZ(EnumAxisPosition.Negative, EnumAxisPosition.Span, EnumAxisPosition.Negative, EnumPositionClass.Edge),
    EdgeNXPZ(EnumAxisPosition.Negative, EnumAxisPosition.Span, EnumAxisPosition.Positive, EnumPositionClass.Edge),
    EdgePXNZ(EnumAxisPosition.Positive, EnumAxisPosition.Span, EnumAxisPosition.Negative, EnumPositionClass.Edge),
    EdgePXPZ(EnumAxisPosition.Positive, EnumAxisPosition.Span, EnumAxisPosition.Positive, EnumPositionClass.Edge),
    EdgeNYNZ(EnumAxisPosition.Span, EnumAxisPosition.Negative, EnumAxisPosition.Negative, EnumPositionClass.Edge),
    EdgeNYPZ(EnumAxisPosition.Span, EnumAxisPosition.Negative, EnumAxisPosition.Positive, EnumPositionClass.Edge),
    EdgePYNZ(EnumAxisPosition.Span, EnumAxisPosition.Positive, EnumAxisPosition.Negative, EnumPositionClass.Edge),
    EdgePYPZ(EnumAxisPosition.Span, EnumAxisPosition.Positive, EnumAxisPosition.Positive, EnumPositionClass.Edge),
    CornerNXNYNZ(EnumAxisPosition.Negative, EnumAxisPosition.Negative, EnumAxisPosition.Negative, EnumPositionClass.Corner),
    CornerNXNYPZ(EnumAxisPosition.Negative, EnumAxisPosition.Negative, EnumAxisPosition.Positive, EnumPositionClass.Corner),
    CornerNXPYNZ(EnumAxisPosition.Negative, EnumAxisPosition.Positive, EnumAxisPosition.Negative, EnumPositionClass.Corner),
    CornerNXPYPZ(EnumAxisPosition.Negative, EnumAxisPosition.Positive, EnumAxisPosition.Positive, EnumPositionClass.Corner),
    CornerPXNYNZ(EnumAxisPosition.Positive, EnumAxisPosition.Negative, EnumAxisPosition.Negative, EnumPositionClass.Corner),
    CornerPXNYPZ(EnumAxisPosition.Positive, EnumAxisPosition.Negative, EnumAxisPosition.Positive, EnumPositionClass.Corner),
    CornerPXPYNZ(EnumAxisPosition.Positive, EnumAxisPosition.Positive, EnumAxisPosition.Negative, EnumPositionClass.Corner),
    CornerPXPYPZ(EnumAxisPosition.Positive, EnumAxisPosition.Positive, EnumAxisPosition.Positive, EnumPositionClass.Corner),
    PostX(EnumAxisPosition.Span, EnumAxisPosition.Centre, EnumAxisPosition.Centre, EnumPositionClass.Post),
    PostY(EnumAxisPosition.Centre, EnumAxisPosition.Span, EnumAxisPosition.Centre, EnumPositionClass.Post),
    PostZ(EnumAxisPosition.Centre, EnumAxisPosition.Centre, EnumAxisPosition.Span, EnumPositionClass.Post);
    public final EnumAxisPosition x;
    public final EnumAxisPosition y;
    public final EnumAxisPosition z;
    public final EnumPositionClass clazz;

    private EnumPosition(EnumAxisPosition var3, EnumAxisPosition var4, EnumAxisPosition var5, EnumPositionClass var6)
    {
        this.x = var3;
        this.y = var4;
        this.z = var5;
        this.clazz = var6;
    }

    public static EnumPosition getCornerPos(int var0, int var1, int var2)
    {
        return var0 < 0 ? (var1 < 0 ? (var2 < 0 ? CornerNXNYNZ : CornerNXNYPZ) : (var2 < 0 ? CornerNXPYNZ : CornerNXPYPZ)) : (var1 < 0 ? (var2 < 0 ? CornerPXNYNZ : CornerPXNYPZ) : (var2 < 0 ? CornerPXPYNZ : CornerPXPYPZ));
    }
}
