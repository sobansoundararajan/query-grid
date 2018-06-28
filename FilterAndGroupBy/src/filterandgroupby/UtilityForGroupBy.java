/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filterandgroupby;

import static filterandgroupby.Main.inputs;
import java.util.*;

/**
 *
 * @author admin
 */
public class UtilityForGroupBy {
    public List<List<Integer>> groupBy(Range r,List<Integer> colList)
    {
        List<List<Integer>>input=new LinkedList<List<Integer>>();
        input=r.getVisibleRows();
         
         Map<List<Object>,List<List<Integer>>>groupByMap=new HashMap<List<Object>,List<List<Integer>>>();
        for(List<Integer>rowList:input)
        {
            HashMap<List<Object>,List<Integer>>tempMap=new HashMap<List<Object>,List<Integer>>();
            for(Integer row:rowList)
            {
                List<Object>groupKey=new LinkedList<Object>();
                for(Integer col:colList)
                {
                     Value v=(Value) inputs.get(row).get(col);
                     groupKey.add(v.getValue());
                }
                tempMap.computeIfAbsent(groupKey, k->new LinkedList()).add(row);
            }
            for(Map.Entry<List<Object>,List<Integer>>map:tempMap.entrySet())
            {
                List<Object> key=map.getKey();
                groupByMap.computeIfAbsent(key, k->new LinkedList ()).add(map.getValue());
            }
        }
        input=new LinkedList<List<Integer>>();
        GroupBy g=new GroupBy(colList,groupByMap);
        for(Map.Entry<List<Object>,List<List<Integer>>>map:groupByMap.entrySet())
        {
            input.addAll(map.getValue());
        }
        r.setVisibleRows(input);
        return input;
    }
}
    

