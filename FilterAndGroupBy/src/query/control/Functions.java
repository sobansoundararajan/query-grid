/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.DataTypes;
import grid.Grid;
import grid.NumberValue;
import grid.StringValue;
import grid.Value;
import java.util.List;
import query.model.FunctionCondition;
import query.model.QueriedRange;
import query.model.QueriedResult;

/**
 *
 * @author admin
 */
public class Functions {

    public static Value sum (List<Value>valueList)
    {
        double sum=0;
        DataTypes type=DataTypes.String;
        for(Value value:valueList)
        {
            type=value.getType();
            if(type.equals(DataTypes.String))
                return new StringValue (DataTypes.String,"");
            else
                sum+=(double)value.getValue();
        }
        return new NumberValue(type,sum);
    }
    public static Value average (List<Value>valueList)
    {
        double avg=0;
        DataTypes type=DataTypes.String;
        for(Value value:valueList)
        {
            type=value.getType();
            if(type.equals(DataTypes.String))
                return new StringValue (DataTypes.String,"");
            else
                avg+=(double)value.getValue();
        }
        return new NumberValue(type,avg/valueList.size());
    }
    public static Value maximum (List<Value>valueList)
    {
        double max=Double.MIN_VALUE;
        DataTypes type=DataTypes.String;
        for(Value value:valueList)
        {
            type=value.getType();
            if(type.equals(DataTypes.String))
                return new StringValue (DataTypes.String,"");
            else
            {
                double val=(double)value.getValue();
                if(max<val)
                    max=val;
            }
        }
        return new NumberValue(type,max);
    }
    public static Value minimum(List<Value>valueList)
    {
        double min=Double.MAX_VALUE;
        DataTypes type=DataTypes.String;
        for(Value value:valueList)
        {
            type=value.getType();
            if(type.equals(DataTypes.String))
                return new StringValue (DataTypes.String,"");
            else
            {
                double val=(double)value.getValue();
                if(min>val)
                    min=val;
            }
        }
        return new NumberValue(type,min);
    }
    public static Value count(List<Value>valueList)
    {
         double count=0;
        DataTypes type=DataTypes.String;
        for(Value value:valueList)
        {
            type=value.getType();
            if(!type.equals(DataTypes.String))
                count++;
        }
        return new NumberValue(type,count);
    }
}
