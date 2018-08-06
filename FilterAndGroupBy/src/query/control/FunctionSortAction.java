/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import query.exception.QueriedException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import query.model.*;

/**
 *
 * @author admin
 */
public class FunctionSortAction {

    private final  Map<Integer, List<FunctionSort>> functionSortConditionMap;

    public FunctionSortAction(Map<Integer, List<FunctionSort>> functionSortConditionMap) {
        this.functionSortConditionMap = functionSortConditionMap;
    }
    
    public void execute(QueriedRange range) throws QueriedException {
        QueriedResult queriedResult = range.getQueriedResult();
        for (Map.Entry<Integer, List<FunctionSort>> entry : functionSortConditionMap.entrySet()) {
            int level=entry.getKey();
            List<FunctionSort>functionSortConditionList=entry.getValue();
            Set<FunctionCondition>functionConditionSet=queriedResult.getFunctionMap().keySet();
            range.getFunctionSortCondition().computeIfAbsent(level, (k)->new LinkedList()).addAll(functionSortConditionList);
            if (level == 0 || level > range.getMaxLevel())
                throw new QueriedException("Operation at this Level : "+level+" can't be perform");
            for(FunctionSort functionSortCondition:functionSortConditionList)
            {
                FunctionCondition functionCondition=functionSortCondition.getFunctionCondition();
                if (!functionConditionSet.contains(functionCondition))
                    throw new QueriedException("This Function : "+functionCondition.getFunction()+" is not yet performed");
            }
            FunctionSortAction.functionOnSortAction(queriedResult, functionSortConditionList, level);
        }
        range.setQueriedResult(queriedResult);
}        

    private static void functionOnSortAction(QueriedResult queriedResult, List<FunctionSort> functionConditionList, int level) {
        if (!queriedResult.getNextAction().isEmpty()) {
            if (level == 0) {
                return;
            }
            level--;
            Collections.sort(queriedResult.getNextAction(), new FuctionCompare(functionConditionList));
            for (QueriedResult chiledNodes : queriedResult.getNextAction()) {
                FunctionSortAction.functionOnSortAction(chiledNodes, functionConditionList, level);
            }
        }
    }
}
