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
import static query.control.FilterAction.conditionMap;
import query.model.Condition;
import query.model.FunctionFilter;
import query.model.FunctionCondition;
import query.model.QueriedRange;
import query.model.QueriedResult;

/**
 *
 * @author admin
 */
public class FilterOnFunctionsAction {

    static Map<Condition, BiFunction<Value, String, Boolean>> conditionMap = new EnumMap<Condition, BiFunction<Value, String, Boolean>>(Condition.class);
    private final Map<Integer, List<FunctionFilter>> conditionList;

    public FilterOnFunctionsAction(Map<Integer, List<FunctionFilter>> conditionList) {
        this.conditionList = conditionList;
    }
    static {
        conditionMap.put(Condition.EQUALS, (v1, v2) -> Utility.equals(v1, v2));
        conditionMap.put(Condition.DOESNOTEQUALS, (v1, v2) -> !Utility.equals(v1, v2));
        conditionMap.put(Condition.GREATERTHAN, (v1, v2) -> Utility.greaterThan(v1, v2));
        conditionMap.put(Condition.GREATERTHANOREQUALTO, (v1, v2) -> !Utility.lessThan(v1, v2));
        conditionMap.put(Condition.LESSTHAN, (v1, v2) -> Utility.lessThan(v1, v2));
        conditionMap.put(Condition.LESSTHANOREQUALTO, (v1, v2) -> !Utility.greaterThan(v1, v2));
    }

    public void execute(Grid grid, QueriedRange range) throws Exception {
        QueriedResult queriedResult = range.getQueriedResult();
        int level = 0;
        for (Map.Entry<Integer, List<FunctionFilter>> entry : conditionList.entrySet()) {
            level = entry.getKey();
            List<FunctionFilter>filterOnFunctionConditionList=entry.getValue();
            Set<FunctionCondition>functionConditionSet=queriedResult.getFunctionMap().keySet();
            if (level == 0 || level > range.getMaxLevel()) {
                throw new QueriedException("Operation at this Level :"+level+" can't be perform");
            }
            range.getFilterOnFunctionConditionMap().computeIfAbsent(level, k -> new LinkedList()).addAll(entry.getValue());
            for (FunctionFilter condition : filterOnFunctionConditionList) {
                FunctionCondition functionCondition=condition.getFuctionCondition();
                if (!functionConditionSet.contains(functionCondition)) 
                    throw new QueriedException("This Function : "+functionCondition+" is not yet performed");
            }
            FilterOnFunctionsAction.FilterOnFunctionAction(grid, range, queriedResult, filterOnFunctionConditionList, level);
        }
        range.setQueriedResult(queriedResult);
        FunctionAction.excute(grid, range);
    }

    private static void FilterOnFunctionAction(Grid grid, QueriedRange range, QueriedResult queriedResult, List<FunctionFilter> conditionList, int level) throws Exception {
        if (level == 0) {
            return;
        }
        List<QueriedResult> nodesToBeAdded = new LinkedList();
        level--;
        for (QueriedResult nextResult : queriedResult.getNextAction()) {
            FilterOnFunctionsAction.FilterOnFunctionAction(grid, range, nextResult, conditionList, level);
            boolean flag = true;
            for (FunctionFilter condition : conditionList) {
                Value value = nextResult.getFunctionMap().get(condition.getFuctionCondition());
                if (!conditionMap.get(condition.getCondition()).apply(value, condition.getValue())) {
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
