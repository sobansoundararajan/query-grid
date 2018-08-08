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
public class Maximum implements Formula {

    @Override
    public String toString() {
        return "Maximum{" + '}';
    }

    @Override
    public Value getValue(List<Value> valueList) {
        double max = Double.MIN_VALUE;
        DataTypes type = DataTypes.String;
        for (Value value : valueList) {
            type = value.getType();
            if (type.equals(DataTypes.String)) {
                return new StringValue(DataTypes.String, "");
            } else {
                double val = (double) value.getValue();
                if (max < val) {
                    max = val;
                }
            }
        }
        return new NumberValue(type, max);
    }

}
