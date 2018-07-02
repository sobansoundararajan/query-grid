/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;
import query.model.ConditionsEnum;
import grid.Grid;
import grid.Value;
import query.model.Condition;
import query.model.QueriedRange;
import query.model.Filter;
import query.model.FilterResult;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
/**
 *
 * @author admin
 */
public class FilterAction {
    
   static Map<ConditionsEnum,BiFunction<Value,String,Boolean>>conditionMap=new EnumMap<ConditionsEnum,BiFunction<Value,String,Boolean>>(ConditionsEnum.class);
   
   public void filter(Grid grid,QueriedRange range,ArrayList<Condition>conList)
    {   
        Filter f=new Filter(conList);
        FilterResult filterResult=new FilterResult();
        Set<Set<Integer>>input=range.getVisibleRows();
        for(Set<Integer>rowList:input)
        { 
            Set<Integer>temp=new HashSet();
            for(Integer row:rowList)
            {
                boolean flag=true;
                for(Condition c:conList)
                {
                    Value v= grid.get(row,c.getCol()+range.startCol);
                    BiFunction<Value,String,Boolean> test =conditionMap.get(c.getCondition());
                    if(!test.apply(v, c.getValue()))
                    {
                        flag=false;
                        break;
                    }
                }
                if(flag)
                    temp.add(row);
            }
            filterResult.add(temp);
        }
     f.setFilterResult(filterResult);
     range.addResult(f);
    }
   static{
                    conditionMap.put(ConditionsEnum.Equals, (v1,v2) -> Utility.equals(v1,v2));
                    conditionMap.put(ConditionsEnum.DoesNotEquals, (v1,v2) -> !Utility.equals(v1,v2));
                    conditionMap.put(ConditionsEnum.GreaterThan, (v1,v2) -> Utility.greaterThan(v1,v2));
                    conditionMap.put(ConditionsEnum.GreaterThanOrEqualTo, (v1,v2) -> !Utility.lessThan(v1,v2));
                    conditionMap.put(ConditionsEnum.LessThan, (v1,v2) -> Utility.lessThan(v1,v2));
                    conditionMap.put(ConditionsEnum.LessThanOrEqualTo, (v1,v2)-> !Utility.greaterThan(v1,v2));
                    conditionMap.put(ConditionsEnum.BeginesWith, (v1,v2)-> Utility.beginsWith(v1,v2));
                    conditionMap.put(ConditionsEnum.DoesNotBeginWith, (v1,v2)-> !Utility.beginsWith(v1,v2));
                    conditionMap.put(ConditionsEnum.EndsWith, (v1,v2)-> Utility.endsWith(v1,v2));
                    conditionMap.put(ConditionsEnum.DoesNotEndsWith, (v1,v2)-> !Utility.endsWith(v1,v2));
                    conditionMap.put(ConditionsEnum.Contains,(v1,v2)-> Utility.contains(v1,v2));
                    conditionMap.put(ConditionsEnum.DoesNotContains,(v1,v2) -> !Utility.contains(v1,v2));
                    conditionMap.put(ConditionsEnum.Matches,(v1,v2) -> Utility.equals(v1,v2));
                    conditionMap.put(ConditionsEnum.DoesNotMatchs,(v1,v2)-> !Utility.equals(v1,v2));
   }
}
