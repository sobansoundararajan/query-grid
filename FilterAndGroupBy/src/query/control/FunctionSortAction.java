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
import query.model.*;

/**
 *
 * @author admin
 */
public class FunctionSortAction {

    public void functionSort(QueriedRange range, List<FunctionCondition> functionConditionList,SortingCriteria sortingCriteria, int level) throws QueriedException {
        if (level == 0 || level > range.getMaxLevel()) {
            throw new QueriedException("Operation at this Level can't be perform");
        }
        QueriedResult queriedResult = range.getQueriedResult();
        if (!queriedResult.getFunctionMap().keySet().containsAll(functionConditionList)) {
            throw new QueriedException("This Function is not yet performed");
        } else {
            FunctionSortAction.action(queriedResult, functionConditionList,sortingCriteria, level);
        }
        range.setQueriedResult(queriedResult);
    }

    private static void action(QueriedResult queriedResult, List<FunctionCondition> functionConditionList,SortingCriteria sortingCriteria, int level) {
        if (!queriedResult.getNextAction().isEmpty()) {
            if (level == 0) {
                return;
            }
            level--;
            if(sortingCriteria.equals(SortingCriteria.ASCENDING))
                Collections.sort(queriedResult.getNextAction(), new FuctionCompare(functionConditionList));
            else
                Collections.sort(queriedResult.getNextAction(), Collections.reverseOrder(new FuctionCompare(functionConditionList)));
            for (QueriedResult chiledNodes : queriedResult.getNextAction()) {
                FunctionSortAction.action(chiledNodes, functionConditionList,sortingCriteria, level);
            }
        }
    }
}