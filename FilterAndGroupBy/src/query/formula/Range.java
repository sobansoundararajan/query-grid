/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.formula;

import grid.DataTypes;
import grid.ErrorValue;
import grid.NumberValue;
import grid.StringValue;
import grid.Value;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author admin
 */
public class Range extends RangedCriteria {

    public Range(List<Double> rangeList) {
        super(rangeList);
    }

    @Override
    public String toString() {
        return "Range{" + '}';
    }

    public Value getValue(List<Value> valueList) {
        Collections.sort(rangeList);
        int size=rangeList.size();
        if (valueList.size() == 1) {
            Value value = valueList.get(0);
            DataTypes type=value.getType();
            if (!(type.equals(DataTypes.String) || type.equals(DataTypes.Date))) {
                double val = (double) value.getValue();
                for (int i = 0; i < size; i++) {
                    if (rangeList.get(i) >= val ) 
                        return new NumberValue(DataTypes.Number,i);
                    else if(i==size-1)
                        return new NumberValue(DataTypes.Number,i+1);
                }
            }
            return new ErrorValue(DataTypes.ERROR, "REF!");
        } else {
            return new ErrorValue(DataTypes.ERROR, "REF!");
        }
    }
}
