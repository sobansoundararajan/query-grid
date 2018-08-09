/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Value;
import grid.DataTypes;

/**
 *
 * @author admin
 */
public class Utility {

    public static boolean equals(Value value, Value condition) {
        if (!value.getType().equals(DataTypes.String)) {
            double val = (double) condition.getValue();
            if (val == (double) value.getValue()) {
                return true;
            }
        } else if (value.getValue().equals(condition)) {
            return true;
        }
        return false;
    }

    public static boolean greaterThan(Value value, Value condition) {
        if (!value.getType().equals(DataTypes.String) && (double) value.getValue() > (double) condition.getValue()) {
            return true;
        }
        return false;
    }

    public static boolean lessThan(Value value, Value condition) {
        if (!value.getType().equals(DataTypes.String) && (double) value.getValue() < (double) condition.getValue()) {
            return true;
        }
        return false;
    }

    public static boolean beginsWith(Value value, Value condition) {
        if (value.getType().equals(DataTypes.String)) {
            String temp = value.getValue().toString();
            if (temp.startsWith((String) condition.getValue())) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Value value, Value condition) {
        if (value.getType().equals(DataTypes.String)) {
            String temp = value.getValue().toString();
            String val = condition.getValue().toString();
            for (int i = 0; i < val.length(); i++) {
                if (temp.indexOf(val.charAt(i)) == -1) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean endsWith(Value value, Value condition) {
        if (value.getType().equals(DataTypes.String)) {
            String temp = value.getValue().toString();
            if (temp.endsWith((String) condition.getValue())) {
                return true;
            }
        }
        return false;
    }

}
