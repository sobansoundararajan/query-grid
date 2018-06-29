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
    public GroupByResult groupBy(Range r,Collection<Integer> colList)
    {
        Set<Set<Integer>>input=new HashSet();
        GroupByResult gp=new GroupByResult();
        input=r.getVisibleRows();
        for(Set<Integer>rowList:input)
        {
            HashMap<List<Object>,Set<Integer>>tempMap=new HashMap();
            for(Integer row:rowList)
            {
                List<Object>groupKey=new LinkedList();
                for(Integer col:colList)
                {
                     Value v=(Value) inputs.get(row).get(col);
                     groupKey.add(v.getValue());
                }
                tempMap.computeIfAbsent(groupKey, k->new HashSet()).add(row);
            }
            for(Map.Entry<List<Object>,Set<Integer>>map:tempMap.entrySet())
            {
                List<Object> key=map.getKey();
                gp.getGroupByMap().computeIfAbsent(key, k-> new VisibleRows()).getVisibleRows().add(map.getValue());
            }
        }
        GroupBy g=new GroupBy(colList,gp);
        r.addResult(g);
        return gp;
    }
}
    

