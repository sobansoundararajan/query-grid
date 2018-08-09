/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Grid;
import query.exception.QueriedException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import query.model.*;

/**
 *
 * @author admin
 */
public class SortOnGroupsAction {

    private final Map<Integer, List<SortOnGroups>> sortOnGroupsMap;

    public SortOnGroupsAction(Map<Integer, List<SortOnGroups>> sortOnGroupsMap) {

        this.sortOnGroupsMap = sortOnGroupsMap;
    }

    public void execute(Grid grid, QueriedRange range) throws QueriedException {
        QueriedResult queriedResult = range.getQueriedResult();
        for (Map.Entry<Integer, List<SortOnGroups>> entry : sortOnGroupsMap.entrySet()) {
            int level = entry.getKey();
            List<SortOnGroups> sortOnGroupsList = entry.getValue();
            range.getSortOnGroupsMap().computeIfAbsent(level, (k) -> new LinkedList()).addAll(sortOnGroupsList);
            if (level == 0 || level > range.getMaxLevel()) {
                throw new QueriedException("Operation at this Level : " + level + " can't be perform");
            }
            functionOnSortAction(grid, range, queriedResult, sortOnGroupsList, level);
        }
        range.setQueriedResult(queriedResult);
    }

    private void functionOnSortAction(Grid grid, QueriedRange range, QueriedResult queriedResult, List<SortOnGroups> sortOnGroupsList, int level) {
        if (!queriedResult.getNextAction().isEmpty()) {
            if (level == 0) {
                return;
            }
            level--;
            Collections.sort(queriedResult.getNextAction(), new GroupCompare(grid, range, sortOnGroupsList));
            for (QueriedResult chiledNodes : queriedResult.getNextAction()) {
                functionOnSortAction(grid,range, chiledNodes, sortOnGroupsList, level);
            }
        }
    }
}
