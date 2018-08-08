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
public class Sum implements Formula {

    @Override
    public String toString() {
        return "Sum{" + '}';
    }

    @Override
    public Value getValue(List<Value> valueList) {
        double sum = 0;
        DataTypes type = DataTypes.String;
        for (Value value : valueList) {
            type = value.getType();
            if (type.equals(DataTypes.String)) {
                return new StringValue(DataTypes.String, "");
            } else {
                sum += (double) value.getValue();
            }
        }
        return new NumberValue(type, sum);
    }

}
