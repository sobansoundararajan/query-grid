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
import query.model.Filter;
import query.model.FilterCondition;
import query.model.FilterOnFunctionCondition;
import query.model.FunctionCondition;
import query.model.FunctionName;
import query.model.FunctionSortCondition;
import query.model.GroupBy;
import query.model.QueriedRange;
import query.model.Sorting;
import query.model.SortingCondition;

/**
 *
 * @author admin
 */
public class RefershResult {

    public void execute(QueriedRange range, Grid grid) throws Exception {
        List<GroupBy> groupByConditionList = range.getGroupByConditionList();
        Map<FunctionCondition, Value> functionMap = range.getQueriedResult().getFunctionMap();
        range = new QueriedRange(range.getStartRow(), range.getEndRow(), range.getStartCol(), range.getEndCol());
        range.getQueriedResult().setFunctionMap(functionMap);
        for (GroupBy groupBy : groupByConditionList) {
            GroupByAction groupByAction = new GroupByAction(groupBy.getGroupByConditionList());
            groupByAction.execute(grid, range);
        }

        Collection<FilterCondition> filterConditions = range.getFilterConList();
        if (!filterConditions.isEmpty()) {
            FilterAction filterAction = new FilterAction(filterConditions);
            range.setFilterCondition(new LinkedList());
            filterAction.execute(grid, range);
        }
        SortingCondition sortingCondition = range.getSortingCondition();
        if (sortingCondition != null) {
            SortAction sortAction = new SortAction(sortingCondition);
            range.setSortingCondition(null);
            sortAction.execute(grid, range);
        }
        Map<Integer, List<FilterOnFunctionCondition>> filterOnFunctionConditionMap = range.getFilterOnFunctionConditionMap();
        if (!filterOnFunctionConditionMap.isEmpty()) {
            range.setFilterOnFunctionConditionMap(new HashMap());
            FilterOnFunctionsAction filterOnFunctionAction = new FilterOnFunctionsAction(filterOnFunctionConditionMap);
            filterOnFunctionAction.execute(grid, range);
        }
        Map<Integer, List<FunctionSortCondition>> functionSortConditionMap = range.getFunctionSortCondition();
        if (!functionSortConditionMap.isEmpty()) {
            range.setFunctionSortCondition(new HashMap());
            FunctionSortAction functionSortAction = new FunctionSortAction(functionSortConditionMap);
            functionSortAction.execute(range);

        }
    }
}
