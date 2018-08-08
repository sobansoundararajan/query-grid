/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Grid;
import query.exception.QueriedException;
import grid.Value;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import query.model.ConditionOperator;
import query.model.FilterOnGroups;
import query.model.ColumnFormula;
import query.model.QueriedRange;
import query.model.QueriedResult;

/**
 *
 * @author admin
 */
public class FilterOnGroupsAction {

    static Map<ConditionOperator, BiFunction<Value, String, Boolean>> conditionMap = new EnumMap<ConditionOperator, BiFunction<Value, String, Boolean>>(ConditionOperator.class);
    private final Map<Integer, List<FilterOnGroups>> filterOnGroupsMap;

    public FilterOnGroupsAction(Map<Integer, List<FilterOnGroups>> filterOnGroupsMap) {
        this.filterOnGroupsMap = filterOnGroupsMap;
    }

    static {
        conditionMap.put(ConditionOperator.EQUALS, (v1, v2) -> Utility.equals(v1, v2));
        conditionMap.put(ConditionOperator.DOESNOTEQUALS, (v1, v2) -> !Utility.equals(v1, v2));
        conditionMap.put(ConditionOperator.GREATERTHAN, (v1, v2) -> Utility.greaterThan(v1, v2));
        conditionMap.put(ConditionOperator.GREATERTHANOREQUALTO, (v1, v2) -> !Utility.lessThan(v1, v2));
        conditionMap.put(ConditionOperator.LESSTHAN, (v1, v2) -> Utility.lessThan(v1, v2));
        conditionMap.put(ConditionOperator.LESSTHANOREQUALTO, (v1, v2) -> !Utility.greaterThan(v1, v2));
    }

    public void execute(Grid grid, QueriedRange range) throws Exception {
        QueriedResult queriedResult = range.getQueriedResult();
        int level = 0;
        for (Map.Entry<Integer, List<FilterOnGroups>> entry : filterOnGroupsMap.entrySet()) {
            level = entry.getKey();
            List<FilterOnGroups> filterOnFunctionConditionList = entry.getValue();
            if (level == 0 || level > range.getMaxLevel()) {
                throw new QueriedException("Operation at this Level :" + level + " can't be perform");
            }
            range.getFilterOnGroupsMap().computeIfAbsent(level, k -> new LinkedList()).addAll(entry.getValue());
            FilterOnGroupsAction(grid, range, queriedResult, filterOnFunctionConditionList, level);
        }
        range.setQueriedResult(queriedResult);
    }

    private void FilterOnGroupsAction(Grid grid, QueriedRange range, QueriedResult queriedResult, List<FilterOnGroups> filterOnGroupsMap, int level) throws Exception {
        if (level == 0) {
            return;
        }
        List<QueriedResult> nodesToBeAdded = new LinkedList();
        level--;
        for (QueriedResult nextResult : queriedResult.getNextAction()) {
            FilterOnGroupsAction(grid, range, nextResult, filterOnGroupsMap, level);
            boolean flag = true;
            for (FilterOnGroups condition : filterOnGroupsMap) {
                Value value = nextResult.evaluateFormula(grid,range, condition.getColumnFormula());
                if (!conditionMap.get(condition.getConditionOperator()).apply(value, condition.getValue())) {
                    flag = false;
                }
            }
            if (flag) {
                nodesToBeAdded.add(nextResult);
            }
        }
        queriedResult.setNextAction(nodesToBeAdded);
    }
}
