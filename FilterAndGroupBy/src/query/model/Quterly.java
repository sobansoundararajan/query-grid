/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import grid.DataTypes;
import grid.ErrorValue;
import grid.StringValue;
import grid.Value;
import java.util.Date;

/**
 *
 * @author admin
 */
public class Quterly implements GroupByCriteria{
     public Value getKey(Value value) {
            if (value.getType().equals(DataTypes.Date)) {
                Date date = new Date((long) (((double) value.getValue()) * 86400000.0));
                String str;
                if (date.getMonth() < 3) {
                    str = "Quter1";
                } else if (date.getMonth() < 6) {
                    str = "Quter2";
                } else if (date.getMonth() < 9) {
                    str = "Queter3";
                } else {
                    str = "Quter4";
                }
                return new StringValue(DataTypes.String, str);
            } else {
                return new ErrorValue(DataTypes.ERROR, "REF!");
            }
        }
    
}
