/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.formula;

import grid.DataTypes;
import grid.ErrorValue;
import grid.NumberValue;
import grid.Value;
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class WeekDays implements Formula {

    @Override
    public String toString() {
        return "WeekDays{" + '}';
    }

    public Value getValue(List<Value> valueList) {
        if (valueList.size() == 1) {
            Value value = valueList.get(0);
            if (value.getType().equals(DataTypes.Date)) {
                Date date = new Date((long) (((double) value.getValue()) * 86400000.0));
                double day = date.getDay();
                return new NumberValue(DataTypes.Number, day);
            } else {
                return new ErrorValue(DataTypes.ERROR, "REF!");
            }
        } else {
            return new ErrorValue(DataTypes.ERROR, "REF!");
        }
    }

}
