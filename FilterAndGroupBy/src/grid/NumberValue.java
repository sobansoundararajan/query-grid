package grid;

import java.util.LinkedList;

//$Id$

public class NumberValue extends Value{
	
	
	final double value;
	public NumberValue(DataTypes type,double value) {
		super(type);
		this.value=value;
	}
	public Object getValue() {
		return value;
	}
	

}
