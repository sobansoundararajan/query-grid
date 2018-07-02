/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filterandgroupby;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
/**
 *
 * @author admin
 */
public class UtilityForFilter {
    
   static Map<ConditionsEnum,BiFunction<Value,String,Boolean>>conditionMap=new EnumMap<ConditionsEnum,BiFunction<Value,String,Boolean>>(ConditionsEnum.class);
   private Boolean equals(Value v,String condition)
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
   private Boolean greaterThan(Value v,String condition) 
   {
       if(!v.type.equals(DataTypes.String)&&(double)v.getValue()>Double.valueOf(condition))
       {
           return true;
       }
       return false;
   }
   private Boolean lessThan(Value v,String condition)
   {
       if(!v.type.equals(DataTypes.String)&&(double)v.getValue()<Double.valueOf(condition))
       {
           return true;
       }
       return false;
   }
   private Boolean beginsWith(Value v,String condition)
   {
       if(v.type.equals(DataTypes.String))
       {    
           String temp=v.getValue().toString();
           if(temp.startsWith(condition))
           return true;
       }
       return false;
   }
   private Boolean contains(Value v,String condition)
   {
       if(v.type.equals(DataTypes.String))
       {
            String temp=v.getValue().toString();
           for(int i=0;i<condition.length();i++)
           {
               if(temp.indexOf(condition.charAt(i))==-1)
                   return false;
           }
           return true;
       }
       return false;
   }
   private Boolean endsWith(Value v,String condition)
   {
       if(v.type.equals(DataTypes.String))
       {
           String temp=v.getValue().toString();
           if(temp.endsWith(condition))
           return true;
       }
       return false;
   }
   static{

        UtilityForFilter util=new UtilityForFilter();
                    
                    conditionMap.put(ConditionsEnum.Equals, (v1,v2) -> util.equals(v1,v2));
                    conditionMap.put(ConditionsEnum.DoesNotEquals, (v1,v2) -> !util.equals(v1,v2));
                    conditionMap.put(ConditionsEnum.GreaterThan, (v1,v2) -> util.greaterThan(v1,v2));
                    conditionMap.put(ConditionsEnum.GreaterThanOrEqualTo, (v1,v2) -> !util.lessThan(v1,v2));
                    conditionMap.put(ConditionsEnum.LessThan, (v1,v2) -> util.lessThan(v1,v2));
                    conditionMap.put(ConditionsEnum.LessThanOrEqualTo, (v1,v2)-> !util.greaterThan(v1,v2));
                    conditionMap.put(ConditionsEnum.BeginesWith, (v1,v2)-> util.beginsWith(v1,v2));
                    conditionMap.put(ConditionsEnum.DoesNotBeginWith, (v1,v2)-> !util.beginsWith(v1,v2));
                    conditionMap.put(ConditionsEnum.EndsWith, (v1,v2)-> util.endsWith(v1,v2));
                    conditionMap.put(ConditionsEnum.DoesNotEndsWith, (v1,v2)-> !util.endsWith(v1,v2));
                    conditionMap.put(ConditionsEnum.Contains,(v1,v2)-> util.contains(v1,v2));
                    conditionMap.put(ConditionsEnum.DoesNotContains,(v1,v2) -> !util.contains(v1,v2));
                    conditionMap.put(ConditionsEnum.Matches,(v1,v2) -> util.equals(v1,v2));
                    conditionMap.put(ConditionsEnum.DoesNotMatchs,(v1,v2)-> !util.equals(v1,v2));
   }
    
    public static void filter(Grid grid,Range range,ArrayList<Condition>conList)
    {   
        FilterResult fr=new FilterResult();
        Set<Set<Integer>>input=range.getVisibleRows();
        for(Set<Integer>rowList:input)
        { 
            Set<Integer>temp=new HashSet();
            for(Integer row:rowList)
            {
                Boolean flag=Boolean.TRUE;
                for(Condition c:conList)
                {
                    Value v= grid.get(row,c.getCol());
                    BiFunction<Value,String,Boolean> test =conditionMap.get(c.getCondition());
                    if(!test.apply(v, c.getValue()))
                        flag=Boolean.FALSE;
                }
                if(flag.equals(Boolean.TRUE))
                    temp.add(row);
            }
            fr.add(temp);
        }
     Filter f=new Filter(conList,fr);
     range.addResult(f);
    }
}
