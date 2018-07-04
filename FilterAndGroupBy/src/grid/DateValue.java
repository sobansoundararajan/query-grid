package grid;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

//$Id$
public class DateValue extends NumberValue {

    LinkedList<DateFormat> dfList;

    public DateValue(DataTypes type, double value) {
        super(type, value);
    }
}
