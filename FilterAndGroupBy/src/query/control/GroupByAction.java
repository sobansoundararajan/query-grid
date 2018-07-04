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

    public void groupBy(Grid grid, QueriedRange range, Collection<Integer> colList) {
        Set<Set<Integer>> input = new HashSet();
        GroupBy g = new GroupBy(colList);
        Map<List<Object>, Set<Set<Integer>>> gr = new HashMap();
        input = range.getVisibleRows();
        for (Set<Integer> rowList : input) {
            HashMap<List<Object>, Set<Integer>> tempMap = new HashMap();
            for (Integer row : rowList) {
                List<Object> groupKey = new LinkedList();
                for (Integer col : colList) {
                    Value v = grid.get(row + range.getStartRow(), col + range.getStartCol());
                    groupKey.add(v.getValue());
                }
                tempMap.computeIfAbsent(groupKey, k -> new HashSet()).add(row);
            }
            for (Map.Entry<List<Object>, Set<Integer>> map : tempMap.entrySet()) {
                List<Object> key = map.getKey();
                gr.computeIfAbsent(key, k -> new HashSet()).add(map.getValue());
            }
        }
        Map<List<Object>, VisibleRows> groupByMap = new HashMap();
        for (List<Object> key : gr.keySet()) {
            VisibleRows visibleRows = new VisibleRows(gr.get(key));
            groupByMap.put(key, visibleRows);
        }
        GroupByResult groupByResult = new GroupByResult(groupByMap);
        g.setGroupByResult(groupByResult);
        range.addResult(g);
    }
}
