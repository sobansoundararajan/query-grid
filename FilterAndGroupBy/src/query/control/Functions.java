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
    
   /* public static Value sum (Grid grid,List<Integer>rows,int col)
    {
         Value value=null;
        double sum=0;
        for(Integer row:rows)
        {
            value=grid.get(row,col);
            if(!value.getType().equals(DataTypes.String))
                sum+=(double)value.getValue();
            else
                return new StringValue (DataTypes.String,"");
        }
        return new NumberValue(value.getType(),sum);
    }
    public static Value average (Grid grid,List<Integer>rows,int col)
    {
         Value value=null;
        double sum=0;
        for(Integer row:rows)
        {
            value=grid.get(row,col);
            if(!value.getType().equals(DataTypes.String))
                sum+=(double)value.getValue();
            else
                return new StringValue (DataTypes.String,"");
        }
        return new NumberValue(value.getType(),sum/rows.size());
    }
    public static Value minimum(Grid grid,List<Integer>rows,int col)
    {
         Value value=null;
        double min=Double.MAX_VALUE;
        for(Integer row:rows)
        {
            value=grid.get(row,col);
            if(!value.getType().equals(DataTypes.String))
            {
                if((double)value.getValue()<min)
                    min=(double)value.getValue();
            }
            else
                return new StringValue (DataTypes.String,"");
        }
        return new NumberValue(value.getType(),min);
    }
    public static Value maximum (Grid grid,List<Integer>rows,int col)
    {
        Value value=null;
        double max=Double.MIN_VALUE;
        for(Integer row:rows)
        {
            value=grid.get(row,col);
            if(!value.getType().equals(DataTypes.String))
            {
                if((double)value.getValue()>max)
                    max=(double)value.getValue();
            }
            else
                return new StringValue (DataTypes.String,"");
        }
        return new NumberValue(value.getType(),max);
    }*/
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
