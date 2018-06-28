package filterandgroupby;

import java.util.LinkedList;

//$Id$

public abstract class Value {
	
	public Value(DataTypes type) {
		this.type=type;
	}
	final DataTypes type;
	public abstract Object getValue();
       
	public DataTypes getType()
	{
		return type;
	}
}
