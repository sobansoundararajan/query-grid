package grid;

import java.util.LinkedList;

//$Id$
public abstract class Value {

    private final DataTypes type;

    public abstract Object getValue();

    public Value(DataTypes type) {
        this.type = type;
    }

    public DataTypes getType() {
        return type;
    }

}
