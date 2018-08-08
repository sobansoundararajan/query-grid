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
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class Quarterly implements Formula {

    @Override
    public String toString() {
        return "Quarterly{" + '}';
    }

    public Value getValue(List<Value> valueList) {
        if (valueList.size() == 1) {
            Value value = valueList.get(0);
            if (value.getType().equals(DataTypes.Date)) {
                Date date = new Date((long) (((double) value.getValue()) * 86400000.0));
                String str;
                if (date.getMonth() < 3) {
                    str = "Q1";
                } else if (date.getMonth() < 6) {
                    str = "Q2";
                } else if (date.getMonth() < 9) {
                    str = "Q3";
                } else {
                    str = "Q4";
                }
                return new StringValue(DataTypes.String, str);
            } else {
                return new ErrorValue(DataTypes.ERROR, "REF!");
            }
        } else {
            return new ErrorValue(DataTypes.ERROR, "REF!");
        }
    }

}
