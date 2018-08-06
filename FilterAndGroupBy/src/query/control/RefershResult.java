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
import query.model.FunctionFilter;
import query.model.FunctionCondition;
import query.model.Function;
import query.model.FunctionSort;
import query.model.GroupBy;
import query.model.QueriedRange;
import query.model.Sort;

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

        Collection<Filter> filterConditions = range.getFilterConList();
        if (!filterConditions.isEmpty()) {
            FilterAction filterAction = new FilterAction(filterConditions);
            range.setFilterCondition(new LinkedList());
            filterAction.execute(grid, range);
        }
        Sort sortingCondition = range.getSortingCondition();
        if (sortingCondition != null) {
            SortAction sortAction = new SortAction(sortingCondition);
            range.setSortingCondition(null);
            sortAction.execute(grid, range);
        }
        Map<Integer, List<FunctionFilter>> filterOnFunctionConditionMap = range.getFilterOnFunctionConditionMap();
        if (!filterOnFunctionConditionMap.isEmpty()) {
            range.setFilterOnFunctionConditionMap(new HashMap());
            FunctionFilterAction filterOnFunctionAction = new FunctionFilterAction(filterOnFunctionConditionMap);
            filterOnFunctionAction.execute(grid, range);
        }
        Map<Integer, List<FunctionSort>> functionSortConditionMap = range.getFunctionSortCondition();
        if (!functionSortConditionMap.isEmpty()) {
            range.setFunctionSortCondition(new HashMap());
            FunctionSortAction functionSortAction = new FunctionSortAction(functionSortConditionMap);
            functionSortAction.execute(range);

        }
    }
}
