/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import grid.DataTypes;
import grid.ErrorValue;
import grid.NumberValue;
import grid.Value;
import java.util.Date;

/**
 *
 * @author admin
 */
public class Month implements GroupByCriteria{

    public Value getKey(Value value) {
            if (value.getType().equals(DataTypes.Date)) {
                Date date = new Date((long) (((double) value.getValue()) * 86400000.0));
                double month = date.getMonth();
                return new NumberValue(DataTypes.Number, month);
            } else {
                return new ErrorValue(DataTypes.ERROR, "REF!");
            }
        }
    
}
