/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Grid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import query.model.FunctionCondition;
import query.model.QueriedRange;
import query.model.QueriedResult;
import query.model.Sort;
import query.model.SortingCriteria;
import query.model.ValueCompare;
import test.Main;

/**
 *
 * @author admin
 */
public class SortAction {
    private final  Sort sortingCondition;

    public SortAction(Sort sortingCondition) {
        this.sortingCondition = sortingCondition;
    }
    

    public void execute(Grid grid, QueriedRange range) throws Exception {
        Sort rangSortingCondition=range.getSortingCondition();
        if(rangSortingCondition==null)
        {
            range.setSortingCondition(sortingCondition);
        }
        else{
        for(Map.Entry<Integer,SortingCriteria>entry:sortingCondition.getSortingCondition().entrySet())
            rangSortingCondition.getSortingCondition().put(entry.getKey(), entry.getValue());
        }
        System.out.println(range.getSortingCondition().getSortingCondition().size());
        QueriedResult queriedResult = range.getQueriedResult();
        SortAction.sortAction(queriedResult, grid, range, sortingCondition);
        range.setQueriedResult(queriedResult);
        if (!range.getQueriedResult().getFunctionMap().isEmpty()) {
            FunctionAction.excute(grid, range);
        }
    }

    private static void sortAction(QueriedResult queriedResult, Grid grid, QueriedRange range, Sort sortingCondition) {
        if (queriedResult.getNextAction().isEmpty()) {
            Collections.sort(queriedResult.getRow(), new ValueCompare(sortingCondition.getSortingCondition(), range, grid));
        } else {
            for (QueriedResult nextQR : queriedResult.getNextAction()) {
                SortAction.sortAction(nextQR, grid, range, sortingCondition);
            }
        }
    }

}
