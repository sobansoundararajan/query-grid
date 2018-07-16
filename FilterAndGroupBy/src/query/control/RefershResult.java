/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Grid;
import grid.Value;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import query.model.Filter;
import query.model.FilterCondition;
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

    public void reEvalute(QueriedRange range, Grid grid) throws Exception {
        List<GroupBy> result = range.getResult();
        Map<FunctionCondition, Value> functionMap = range.getQueriedResult().getFunctionMap();
        range = new QueriedRange(range.getStartRow(), range.getEndRow(), range.getStartCol(), range.getEndCol());
        range.getQueriedResult().setFunctionMap(functionMap);
        for (GroupBy groupBy : result) {
            GroupByAction groupByAction = new GroupByAction();
            groupByAction.groupBy(grid, range, groupBy.getGroupByConditionList());
        }

        if (!range.getFilterConList().isEmpty()) {
            FilterAction filterAction = new FilterAction();
            Collection<FilterCondition> filterConditions = range.getFilterConList();
            range.getFilterConList().removeAll(filterConditions);
            filterAction.filter(grid, range, filterConditions);
        }
        if (!range.getSortingCondition().isEmpty()) {
            SortAction sortAction = new SortAction();
            Collection<SortingCondition> sortingCondition = range.getSortingCondition();
            range.getSortingCondition().removeAll(sortingCondition);
            for (SortingCondition sortCondition : sortingCondition) {
                sortAction.sort(grid, range, sortCondition);
            }
        }
        if(!range.getFilterOnFunctionConditionList().isEmpty())
        {
            FilterOnFunctionsAction filterOnFunctionAction=new FilterOnFunctionsAction ();
            filterOnFunctionAction.filterOnFunction(grid, range, range.getFilterOnFunctionConditionList());
        }
        if(!range.getFunctionSortCondition().isEmpty())
        {
            List<FunctionSortCondition>functionSortConditionList=range.getFunctionSortCondition();
            FunctionSortAction functionSortAction=new FunctionSortAction();
            for(FunctionSortCondition functionSortCondition:functionSortConditionList)
            {
                functionSortAction.functionSort(range, functionSortCondition);
            }
                    
        }
    }
}
