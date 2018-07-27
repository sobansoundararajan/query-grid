/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.DataTypes;
import grid.Grid;
import grid.Value;
import java.text.ParseException;
import query.model.*;
import java.util.*;

/**
 *
 * @author admin
 */
public class GroupByAction {
     private final List<GroupByCondition>groupByConditionList;

    public GroupByAction(List<GroupByCondition> groupByConditionList) {
        this.groupByConditionList = groupByConditionList;
    }
     

    public void execute(Grid grid, QueriedRange range) throws Exception {

        GroupBy groupBy = new GroupBy(groupByConditionList);
        QueriedResult orginalResult = range.getQueriedResult();
        GroupByAction.groupByAction(grid, range, orginalResult, groupByConditionList);
        range.setQueriedResult(orginalResult);
        range.addResult(groupBy);
        if (!range.getQueriedResult().getFunctionMap().isEmpty()) {
            FunctionAction.excute(grid, range);
        }
        
    }

    private static void groupByAction(Grid grid, QueriedRange range, QueriedResult orginalResult, List<GroupByCondition>groupByConditionList) throws ParseException {
        if (orginalResult.getNextAction().isEmpty()) {
            HashMap<List<Value>, List<Integer>> tempMap = new HashMap();
            for (Integer row : orginalResult.getRow()) {
                List<Value> groupKey = new LinkedList();
                for (GroupByCondition groupByCondition: groupByConditionList) {
                    Value value = grid.get(row + range.getStartRow(), groupByCondition.getCol() + range.getStartCol());  
                    GroupByCriteria groupByCriteria=groupByCondition.getGroupByCriteria();
                    groupKey.add(groupByCriteria.getKey(value));
                }
                tempMap.computeIfAbsent(groupKey, k -> new LinkedList()).add(row);
            }
            orginalResult.setRow(new LinkedList());
            for (Map.Entry<List<Value>, List<Integer>> entry : tempMap.entrySet()){ 
                orginalResult.addNextAction(new QueriedResult(entry.getValue(), entry.getKey()));
                
            }
        } else {

            for (QueriedResult vn : orginalResult.getNextAction()) {
                GroupByAction.groupByAction(grid, range, vn, groupByConditionList);
            }
        }
    }
}
