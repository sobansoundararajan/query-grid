/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Grid;
import grid.Value;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import query.model.FilterOnRecords;
import query.model.FilterOnGroups;
import query.model.ColumnFormula;
import query.model.SortOnGroups;
import query.model.GroupBy;
import query.model.QueriedRange;
import query.model.SortOnRecords;

/**
 *
 * @author admin
 */
public class RefershResult {

    public void execute(QueriedRange range, Grid grid) throws Exception {
        List<GroupBy> groupByConditionList = range.getGroupByList();
        Map<ColumnFormula, Value> functionMap = range.getQueriedResult().getFunctionMap();
        range = new QueriedRange(range.getStartRow(), range.getEndRow(), range.getStartCol(), range.getEndCol());
        range.getQueriedResult().setFunctionMap(functionMap);
        for (GroupBy groupBy : groupByConditionList) {
            GroupByAction groupByAction = new GroupByAction(groupBy.getcolumnFormulaList());
            groupByAction.execute(grid, range);
        }

        Collection<FilterOnRecords> filterConditions = range.getFilterOnRecordsList();
        if (!filterConditions.isEmpty()) {
            FilterOnRecordsAction filterAction = new FilterOnRecordsAction(filterConditions);
            range.setFilterOnRecordsList(new LinkedList());
            filterAction.execute(grid, range);
        }
        SortOnRecords sortingCondition = range.getSortOnRecords();
        if (sortingCondition != null) {
            SortOnRecordsAction sortAction = new SortOnRecordsAction(sortingCondition);
            range.setSortOnRecords(null);
            sortAction.execute(grid, range);
        }
        Map<Integer, List<FilterOnGroups>> filterOnFunctionConditionMap = range.getFilterOnGroupsMap();
        if (!filterOnFunctionConditionMap.isEmpty()) {
            range.setFilterOnGroupsMap(new HashMap());
            FilterOnGroupsAction filterOnFunctionAction = new FilterOnGroupsAction(filterOnFunctionConditionMap);
            filterOnFunctionAction.execute(grid, range);
        }
        Map<Integer, List<SortOnGroups>> functionSortConditionMap = range.getSortOnGroupsMap();
        if (!functionSortConditionMap.isEmpty()) {
            range.setSortOnGroupsMap(new HashMap());
            SortOnGroupsAction sortOnGroupsAction = new SortOnGroupsAction(functionSortConditionMap);
            sortOnGroupsAction.execute(grid, range);

        }
    }
}
