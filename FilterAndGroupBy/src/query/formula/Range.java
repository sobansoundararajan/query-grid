/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.formula;

import grid.DataTypes;
import grid.ErrorValue;
import grid.StringValue;
import grid.Value;
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
        if (valueList.size() == 1) {
            Value value = valueList.get(0);
            if (!(value.getType().equals(DataTypes.String) || value.getType().equals(DataTypes.Date))) {
                double val = (double) value.getValue();
                for (int i = 0; i < rangeList.size(); i++) {
                    if (rangeList.get(i) >= val && i == 0) {
                        return new StringValue(DataTypes.String, "Rang { " + Double.MIN_VALUE + "," + rangeList.get(i) + " }");
                    } else if (rangeList.get(i) >= val) {
                        return new StringValue(DataTypes.String, "Rang { " + (rangeList.get(i - 1) + 1) + "," + rangeList.get(i) + " }");
                    } else if (rangeList.get(i) < val && i == rangeList.size() - 1) {
                        return new StringValue(DataTypes.String, "Rang { " + rangeList.get(i) + "," + Double.MAX_VALUE + " }");
                    }
                }
            }
            return new ErrorValue(DataTypes.ERROR, "REF!");
        } else {
            return new ErrorValue(DataTypes.ERROR, "REF!");
        }
    }
}
