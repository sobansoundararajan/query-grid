/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Grid;
import java.util.Collections;
import java.util.Map;
import query.model.QueriedRange;
import query.model.QueriedResult;
import query.model.SortOnRecords;
import query.model.SortingCriteria;
import query.model.ValueCompare;

/**
 *
 * @author admin
 */
public class SortOnRecordsAction {

    private final SortOnRecords sortOnRecords;

    public SortOnRecordsAction(SortOnRecords sortOnRecords) {
        this.sortOnRecords = sortOnRecords;
    }

    public void execute(Grid grid, QueriedRange range) throws Exception {
        SortOnRecords rangSortingCondition = range.getSortOnRecords();
        if (rangSortingCondition == null) {
            range.setSortOnRecords(sortOnRecords);
        } else {
            for (Map.Entry<Integer, SortingCriteria> entry : sortOnRecords.getSortingCondition().entrySet()) {
                rangSortingCondition.getSortingCondition().put(entry.getKey(), entry.getValue());
            }
        }
        System.out.println(range.getSortOnRecords().getSortingCondition().size());
        QueriedResult queriedResult = range.getQueriedResult();
        SortOnRecordsAction.sortAction(queriedResult, grid, range, sortOnRecords);
        range.setQueriedResult(queriedResult);
    }

    private static void sortAction(QueriedResult queriedResult, Grid grid, QueriedRange range, SortOnRecords sortingCondition) {
        if (queriedResult.getNextAction().isEmpty()) {
            Collections.sort(queriedResult.getRow(), new ValueCompare(sortingCondition.getSortingCondition(), range, grid));
        } else {
            for (QueriedResult nextQR : queriedResult.getNextAction()) {
                SortOnRecordsAction.sortAction(nextQR, grid, range, sortingCondition);
            }
        }
    }

}
