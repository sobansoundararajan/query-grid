/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import grid.Grid;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author admin
 */
public class GroupCompare implements Comparator<QueriedResult> {

    private final Grid grid;
    private final QueriedRange range;
    private final List<SortOnGroups> sortOnGroupsList;
    
    public GroupCompare(Grid grid, QueriedRange range, List<SortOnGroups> sortOnGroupsList) {
        this.grid = grid;
        this.range=range;
        this.sortOnGroupsList = sortOnGroupsList;
    }

    @Override
    public int compare(QueriedResult queriedResult1, QueriedResult queriedResult2) {
        for (SortOnGroups functionSortCondition : this.sortOnGroupsList) {
            ColumnFormula functionCondition = functionSortCondition.getColumnFormula();
            double val1 = (double) queriedResult1.getFunctionMap().computeIfAbsent(functionCondition, (k) -> queriedResult1.evaluateFormula(grid, range.getStartRow(), functionCondition)).getValue();
            double val2 = (double) queriedResult2.getFunctionMap().computeIfAbsent(functionCondition, (k) -> queriedResult2.evaluateFormula(grid, range.getStartRow(), functionCondition)).getValue();
            if (val1 != val2) {
                if (functionSortCondition.getSortingCriteria().equals(SortingCriteria.ASCENDING)) {
                    return (int) (val1 - val2);
                } else {
                    return ((int) (val1 - val2)) * -1;
                }
            }
        }
        return 0;
    }

}
