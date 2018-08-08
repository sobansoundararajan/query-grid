/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.formula;

import grid.DataTypes;
import grid.ErrorValue;
import grid.Value;
import java.util.List;

/**
 *
 * @author admin
 */
public class Normal implements Formula {

    @Override
    public String toString() {
        return "Normal{" + '}';
    }

    public Value getValue(List<Value> valueList) {
        if (valueList.size() == 1) {
            Value value = valueList.get(0);
            return value;
        } else {
            return new ErrorValue(DataTypes.ERROR, "REF!");
        }
    }
}
