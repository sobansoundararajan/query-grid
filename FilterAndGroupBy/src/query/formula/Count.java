/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.formula;

import grid.DataTypes;
import grid.NumberValue;
import grid.Value;
import java.util.List;

/**
 *
 * @author admin
 */
public class Count implements Formula {

    @Override
    public String toString() {
        return "Count{" + '}';
    }

    @Override
    public Value getValue(List<Value> valueList) {
        double count = 0;
        DataTypes type = DataTypes.String;
        for (Value value : valueList) {
            type = value.getType();
            if (!type.equals(DataTypes.String)) {
                count++;
            }
        }
        return new NumberValue(type, count);
    }

}
