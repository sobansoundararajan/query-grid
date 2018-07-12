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
import java.util.function.BiFunction;
import static query.control.FilterAction.conditionMap;
import query.model.Condition;
import query.model.FilterOnFunctionCondition;
import query.model.FunctionCondition;
import query.model.QueriedRange;
import query.model.QueriedResult;

/**
 *
 * @author admin
 */
public class FilterOnFunctionsAction {

    static Map<Condition, BiFunction<Value, String, Boolean>> conditionMap = new EnumMap<Condition, BiFunction<Value, String, Boolean>>(Condition.class);

    static {
        conditionMap.put(Condition.EQUALS, (v1, v2) -> Utility.equals(v1, v2));
        conditionMap.put(Condition.DOESNOTEQUALS, (v1, v2) -> !Utility.equals(v1, v2));
        conditionMap.put(Condition.GREATERTHAN, (v1, v2) -> Utility.greaterThan(v1, v2));
        conditionMap.put(Condition.GREATERTHANOREQUALTO, (v1, v2) -> !Utility.lessThan(v1, v2));
        conditionMap.put(Condition.LESSTHAN, (v1, v2) -> Utility.lessThan(v1, v2));
        conditionMap.put(Condition.LESSTHANOREQUALTO, (v1, v2) -> !Utility.greaterThan(v1, v2));
    }

    public void filterOnFunction(Grid grid,QueriedRange range, List<FilterOnFunctionCondition> conditionList, int level) throws Exception {
        if(level==0||level>range.getMaxLevel())
        {
            throw new QueriedException("Operation at this Level can't be perform");
        }
        QueriedResult queriedResult = range.getQueriedResult();
        List<FunctionCondition>functionCondition=new LinkedList ();
        for(FilterOnFunctionCondition condition:conditionList)
        {
            functionCondition.add(condition.getFuctionCondition());
        }
        if (!queriedResult.getFunctionMap().keySet().containsAll(functionCondition)) {
            throw new QueriedException("This Function is not yet performed");
        } else {
            FilterOnFunctionsAction.action(grid,range,queriedResult, conditionList, level);
        }
        range.setQueriedResult(queriedResult);
        FunctionAction.function(grid, range);
    }

    private static void action(Grid grid,QueriedRange range,QueriedResult queriedResult, List<FilterOnFunctionCondition> conditionList, int level) throws Exception {
        if(level==0)
            return ;
        List<QueriedResult> nodesToBeAdded = new LinkedList();
        level--;
        for (QueriedResult nextResult : queriedResult.getNextAction()) {
            FilterOnFunctionsAction.action(grid,range,nextResult, conditionList, level);
            boolean flag = true;
            for (FilterOnFunctionCondition condition : conditionList) {
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
