/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Grid;
import grid.Value;
import query.model.*;
import java.util.*;

/**
 *
 * @author admin
 */
public class GroupByAction {

    public void groupBy(Grid grid, QueriedRange range, Collection<Integer> colList) throws Exception {

        GroupBy groupBy = new GroupBy(colList);
        QueriedResult orginalResult = range.getQueriedResult();
        GroupByAction.action(grid, range, orginalResult, colList);
        range.setQueriedResult(orginalResult);
        range.addResult(groupBy);
        if (!range.getQueriedResult().getFunctionMap().isEmpty()) {
            FunctionAction.function(grid, range);
        }
        
    }

    private static void action(Grid grid, QueriedRange range, QueriedResult orginalResult, Collection<Integer> colList) {
        if (orginalResult.getNextAction().isEmpty()) {
            HashMap<List<Object>, List<Integer>> tempMap = new HashMap();
            for (Integer row : orginalResult.getRow()) {
                List<Object> groupKey = new LinkedList();
                for (Integer col : colList) {
                    Value v = grid.get(row + range.getStartRow(), col + range.getStartCol());
                    groupKey.add(v.getValue());
                }
                tempMap.computeIfAbsent(groupKey, k -> new LinkedList()).add(row);
            }
            orginalResult.setRow(new LinkedList());
            for (Map.Entry<List<Object>, List<Integer>> entry : tempMap.entrySet()){ 
                orginalResult.addNextAction(new QueriedResult(entry.getValue(), entry.getKey()));
                
            }
        } else {

            for (QueriedResult vn : orginalResult.getNextAction()) {
                GroupByAction.action(grid, range, vn, colList);
            }
        }
    }
}
