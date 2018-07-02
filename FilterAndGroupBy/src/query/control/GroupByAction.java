/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Grid;
import grid.Value;
import query.model.VisibleRows;
import query.model.QueriedRange;
import query.model.GroupBy;
import query.model.GroupByResult;
import java.util.*;

/**
 *
 * @author admin
 */
public class GroupByAction {
    public void groupBy(Grid grid,QueriedRange range,Collection<Integer> colList)
    {
        Set<Set<Integer>>input=new HashSet();
        GroupBy g=new GroupBy(colList);
        GroupByResult groupByResult=new GroupByResult();
        input=range.getVisibleRows();
        for(Set<Integer>rowList:input)
        {
            HashMap<List<Object>,Set<Integer>>tempMap=new HashMap();
            for(Integer row:rowList)
            {
                List<Object>groupKey=new LinkedList();
                for(Integer col:colList)
                {
                     Value v= grid.get(row,col+range.startCol);
                     groupKey.add(v.getValue());
                }
                tempMap.computeIfAbsent(groupKey, k->new HashSet()).add(row);
            }
            for(Map.Entry<List<Object>,Set<Integer>>map:tempMap.entrySet())
            {
                List<Object> key=map.getKey();
                groupByResult.getGroupByMap().computeIfAbsent(key, k-> new VisibleRows()).getVisibleRows().add(map.getValue());
            }
        }
        
        g.setGroupByResult(groupByResult);
        range.addResult(g);
    }
}
    

