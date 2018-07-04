package test;

import grid.StringValue;
import grid.DateFormat;
import grid.Value;
import grid.BooleanValue;
import grid.DataTypes;
import grid.DateValue;
import grid.NumberValue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//$Id$
public class ValueParser {

    static int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static boolean isNum(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static Value TypeandValue(String input, LinkedList<DateFormat> df) throws ParseException {
        Value value;
        double num;
        if (input.charAt(input.length() - 1) == '%') {
            if (isNum(input.substring(0, input.length() - 1))) {
                num = Double.parseDouble(input.substring(0, input.length() - 1));
                value = new NumberValue(DataTypes.Percentage, num);
            } else {
                value = new StringValue(DataTypes.String, input);
            }
        } else if (input.charAt(0) == '$') {
            if (isNum(input.substring(1, input.length()))) {
                num = Double.parseDouble(input.substring(1, input.length()));
                value = new NumberValue(DataTypes.Currency, num);
            } else {
                value = new StringValue(DataTypes.String, input);
            }
        } else if (input.equalsIgnoreCase("true")) {
            value = new BooleanValue(DataTypes.Boolean, 1.0);
        } else if (input.equalsIgnoreCase("false")) {
            value = new BooleanValue(DataTypes.Boolean, 0.0);
        } else if (!input.matches(".*[a-zA-Z]+.*") && (input.contains("/"))) {
            int f = 1, i;
            HashMap<DateFormat, Integer> dm = new HashMap<DateFormat, Integer>();
            String[] temp = input.split("/");
            String format = "";
            if (temp.length == 3) {
                for (i = 0; i < 3; i++) {
                    if (!isNum(temp[i])) {
                        f = 0;
                        value = new StringValue(DataTypes.String, input);
                    }
                }
            } else {
                f = 0;
            }
            if (f == 1) {
                for (i = 0; i < 3; i++) {
                    int t = Integer.valueOf(temp[i]);
                    dm.put(df.get(i), t);
                    if (df.get(i) == DateFormat.d) {
                        format += "dd/";
                    } else if (df.get(i) == DateFormat.m) {
                        format += "mm/";
                    } else if (df.get(i) == DateFormat.y && temp[i].length() == 4) {
                        format += "yyyy/";
                    } else if (df.get(i) == DateFormat.y && temp[i].length() == 2) {
                        format += "yy/";
                    }
                }
                format = format.substring(0, format.length() - 1);
            }
            if (dm.get(DateFormat.m) > 0 && dm.get(DateFormat.m) <= 12 && (dm.get(DateFormat.d) <= days[dm.get(DateFormat.m)] || ((dm.get(DateFormat.y) % 4 == 0) && dm.get(DateFormat.m) == 2) && dm.get(DateFormat.d) == 29)) {
                Date date = new SimpleDateFormat(format).parse(input);

                num = date.getTime() / 86400000.0;
                value = new DateValue(DataTypes.Date, num);
            } else {
                value = new StringValue(DataTypes.String, input);
            }
        } else if (isNum(input)) {
            num = Double.parseDouble(input);
            value = new NumberValue(DataTypes.Number, num);
        } else {
            value = new StringValue(DataTypes.String, input);
        }
        return value;
    }
}
