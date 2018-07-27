/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import grid.DataTypes;
import grid.Grid;
import grid.Value;
import java.util.Comparator;
import java.util.Map;
import query.model.QueriedRange;

/**
 *
 * @author admin
 */
public class ValueCompare implements Comparator<Integer> {

    private final Map<Integer,SortingCriteria> sortingCondition;
    private final QueriedRange range;
    private final Grid grid;

    public ValueCompare(Map<Integer,SortingCriteria> sortingCondition, QueriedRange range, Grid grid) {
        this.sortingCondition = sortingCondition;
        this.range = range;
        this.grid = grid;
    }

    @Override
    public int compare(Integer r1, Integer r2) {

        for (Map.Entry<Integer,SortingCriteria>entry:sortingCondition.entrySet()) {
            int col=entry.getKey();
            if (grid.get(r1,col).getType().equals(DataTypes.String)) {
                int value = grid.get(r1 + range.getStartRow(), col + range.getStartCol()).getValue().toString().compareTo(grid.get(r2 + range.getStartRow(), col + range.getStartCol()).getValue().toString());
                if (value != 0) {
                    if(entry.getValue().equals(SortingCriteria.ASCENDING))
                        return value;
                    else
                        return -1*value;
                }
            } else {
                int value = (int) (((double) grid.get(r1 + range.getStartRow(), col + range.getStartCol()).getValue()) - ((double) grid.get(r1 + range.getStartRow(), col + range.getStartCol()).getValue()));
                if (value != 0) {
                    if(entry.getValue().equals(SortingCriteria.ASCENDING))
                        return value;
                    else
                        return -1*value;
                }
            }
        }
        return 0;
    }

}
