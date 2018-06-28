package filterandgroupby;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//$Id$

public class Source {
	static int [] days={0,31,28,31,30,31,30,31,31,30,31,30,31};
	public static boolean isNum(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	public static Value TypeandValue(String input,LinkedList<DateFormat>df) throws ParseException
	{
		Value v;
		double value;
		if(input.charAt(input.length()-1)=='%')
		{
			if(isNum(input.substring(0, input.length()-1)))
			{
				value=Double.parseDouble(input.substring(0, input.length()-1));
			v=new NumberValue(DataTypes.Percentage,value);
			}
			else
			v=new StringValue(DataTypes.String,input);	
		}
		else if(input.charAt(0)=='$')
		{
			if(isNum(input.substring(1,input.length())))
			{
				value=Double.parseDouble(input.substring(1,input.length()));
			v=new NumberValue(DataTypes.Currency,value);
			}
			else
			v=new StringValue(DataTypes.String,input);
		}
		else if(input.equalsIgnoreCase("true"))
		{
			v=new BooleanValue(DataTypes.Boolean,1.0);
		}
		else if(input.equalsIgnoreCase("false"))
		{
			v=new BooleanValue(DataTypes.Boolean,0.0);
		}
		else if(!input.matches(".*[a-zA-Z]+.*")&&(input.contains("/")))
		{	
			int f=1,i;
			HashMap<DateFormat,Integer>dm=new HashMap<DateFormat,Integer>();
			String [] temp=input.split("/");
			String format="";
			if(temp.length==3)
			{
				for(i=0;i<3;i++)
				{
					if(!isNum(temp[i]))
					{
						f=0;
						v=new StringValue(DataTypes.String,input);	
					}
				}
			}
			else
				f=0;
			if(f==1)
			{
				for(i=0;i<3;i++)
				{
					int t=Integer.valueOf(temp[i]);
					dm.put(df.get(i),t );
					if(df.get(i)==DateFormat.d)
						format+="dd/";
					else if(df.get(i)==DateFormat.m)
						format+="mm/";
					else if(df.get(i)==DateFormat.y&&temp[i].length()==4)
						format+="yyyy/";
					else if(df.get(i)==DateFormat.y&&temp[i].length()==2)
						format+="yy/";
				}
				format=format.substring(0, format.length()-1);
			}
			if(dm.get(DateFormat.m)>0&&dm.get(DateFormat.m)<=12&&(dm.get(DateFormat.d)<=days[dm.get(DateFormat.m)]||((dm.get(DateFormat.y)%4==0)&&dm.get(DateFormat.m)==2)&&dm.get(DateFormat.d)==29))
			{
				Date date = new SimpleDateFormat(format).parse(input);
				
				value=date.getTime()/86400000.0;
				v=new DateValue(DataTypes.Date,value);
			}
			else
			{
				v=new StringValue(DataTypes.String,input);
			}
			/*int f=0,dd=100,mm=100,year=50000;
			if(isNum(input.substring(0,2)))
			dd=Integer.valueOf(input.substring(0,2));
			if(isNum(input.substring(3, 5)))
			mm=Integer.valueOf(input.substring(3, 5));
			if(isNum(input.substring(6,input.length())))
				year=Integer.valueOf(input.substring(6,input.length()));
		
			if((dd>0&&dd<=12&&days[dd]>=mm)||(mm>0&&mm<=12&&days[mm]>=dd)||(year%4==0&&((dd==2&&mm==29)||(mm==2&&dd==29))))
			{	
				Date date = null;
				if(df.getFirst().equals(DateFormat.dd)&&(mm<=12))
				 date = new SimpleDateFormat("dd/MM/yyyy").parse(input);
				 else if(df.getFirst().equals(DateFormat.valueOf("mm"))&&dd<=12)
				 date = new SimpleDateFormat("MM/dd/yyyy").parse(input);
				 else 
				 {
					 f=1;
				 }
				if(f==0)
				{
				value=date.getTime();
				v=new DateValue(DataTypes.valueOf("Date"),value);
				}
				else
				v=new StringValue(DataTypes.valueOf("String"),input);
			}
			else
				v=new StringValue(DataTypes.valueOf("String"),input);*/
		}
		else if(isNum(input))
		{
			value=Double.parseDouble(input);
			v=new NumberValue(DataTypes.Number,value);
		}
		else
		{
			v=new StringValue(DataTypes.String,input);
		}
		return v;
	}
}
