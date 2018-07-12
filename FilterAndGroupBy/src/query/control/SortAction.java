/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Grid;
import java.util.Collections;
import java.util.List;
import query.model.FunctionCondition;
import query.model.QueriedRange;
import query.model.QueriedResult;
import query.model.Sorting;
import query.model.SortingCondition;
import query.model.SortingCriteria;
import query.model.ValueCompare;
import test.Main;

/**
 *
 * @author admin
 */
public class SortAction {

    public void sort(Grid grid, QueriedRange range, SortingCondition sortingCondition) throws Exception {
        //Sorting sort = new Sorting(sortingCondition);
        range.getSortingCondition().add(sortingCondition);
        QueriedResult queriedResult = range.getQueriedResult();
        SortAction.action(queriedResult, grid, range, sortingCondition);
        range.setQueriedResult(queriedResult);
        if (!range.getQueriedResult().getFunctionMap().isEmpty()) {
            FunctionAction.function(grid, range);
        }
    }

    private static void action(QueriedResult queriedResult, Grid grid, QueriedRange range, SortingCondition sortingCondition) {
        if (queriedResult.getNextAction().isEmpty()) {
            if (sortingCondition.getSortingCriteria().equals(SortingCriteria.ASCENDING)) {
                Collections.sort(queriedResult.getRow(), new ValueCompare(sortingCondition.getCol(), range, grid));
            } else {
                Collections.sort(queriedResult.getRow(), Collections.reverseOrder(new ValueCompare(sortingCondition.getCol(), range, grid)));
            }
        } else {
            for (QueriedResult nextQR : queriedResult.getNextAction()) {
                QueriedResult nextCR = new QueriedResult(nextQR.getRow(), nextQR.getValue());
                SortAction.action(nextQR, grid, range, sortingCondition);
            }
        }
    }

}
