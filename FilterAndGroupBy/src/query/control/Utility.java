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
    public static boolean equals(Value v,String condition)
   {
         if(!v.type.equals(DataTypes.String))
         {
             double val=Double.valueOf(condition);
             if(val==(double)v.getValue())
                return true;
        }
        else if(v.getValue().equals(condition))
        {
             return true;
        }  
         return false;
   }
   public static boolean greaterThan(Value v,String condition) 
   {
       if(!v.type.equals(DataTypes.String)&&(double)v.getValue()>Double.valueOf(condition))
       {
           return true;
       }
       return false;
   }
   public static boolean lessThan(Value v,String condition)
   {
       if(!v.type.equals(DataTypes.String)&&(double)v.getValue()<Double.valueOf(condition))
       {
           return true;
       }
       return false;
   }
   public static boolean beginsWith(Value v,String condition)
   {
       if(v.type.equals(DataTypes.String))
       {    
           String temp=v.getValue().toString();
           if(temp.startsWith(condition))
           return true;
       }
       return false;
   }
   public static boolean contains(Value value,String condition)
   {
       if(value.type.equals(DataTypes.String))
       {
            String temp=value.getValue().toString();
           for(int i=0;i<condition.length();i++)
           {
               if(temp.indexOf(condition.charAt(i))==-1)
                   return false;
           }
           return true;
       }
       return false;
   }
   public static boolean endsWith(Value v,String condition)
   {
       if(v.type.equals(DataTypes.String))
       {
           String temp=v.getValue().toString();
           if(temp.endsWith(condition))
           return true;
       }
       return false;
   }
    
}
