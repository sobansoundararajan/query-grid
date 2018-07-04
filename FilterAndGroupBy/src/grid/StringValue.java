package grid;

//$Id$
public class StringValue extends Value {

    private final String value;

    public StringValue(DataTypes type, String value) {
        super(type);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
