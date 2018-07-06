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
        QueriedResult clonedResult = new QueriedResult(orginalResult.getRow(),orginalResult.getValue());
        GroupByAction.action(grid, range, orginalResult,clonedResult, colList);
        groupBy.setQueriedResult(clonedResult);
        range.addResult(groupBy);
    }

    private static void action(Grid grid, QueriedRange range, QueriedResult orginalResult,QueriedResult clonedResult, Collection<Integer> colList) {
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
            clonedResult.setRow(new LinkedList ());
            for (Map.Entry<List<Object>, List<Integer>> entry : tempMap.entrySet()) {
                clonedResult.addNextAction(new QueriedResult(entry.getValue(), entry.getKey()));
            }
        } else {
            
            for (QueriedResult vn : orginalResult.getNextAction()) {
                QueriedResult qr=new QueriedResult(vn.getRow(),vn.getValue());
                clonedResult.addNextAction(qr);
                GroupByAction.action(grid, range, vn,qr, colList);
            }
        }
    }
}
