/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.formula;

import grid.DataTypes;
import grid.NumberValue;
import grid.StringValue;
import grid.Value;
import java.util.List;

/**
 *
 * @author admin
 */
public class Minimum implements Formula {

    @Override
    public String toString() {
        return "Minimum{" + '}';
    }

    @Override
    public Value getValue(List<Value> valueList) {
        double min = Double.MAX_VALUE;
        DataTypes type = DataTypes.String;
        for (Value value : valueList) {
            type = value.getType();
            if (type.equals(DataTypes.String)) {
                return new StringValue(DataTypes.String, "");
            } else {
                double val = (double) value.getValue();
                if (min > val) {
                    min = val;
                }
            }
        }
        return new NumberValue(type, min);
    }

}
