/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Grid;
import java.util.List;
import query.model.Filter;
import query.model.GroupBy;
import query.model.GroupByAndFilter;
import query.model.QueriedRange;
import query.model.Sorting;

/**
 *
 * @author admin
 */
public class RefershResult {
    public void reEvalute(QueriedRange range, Grid grid) throws Exception {
        List<GroupByAndFilter> result = range.getResult();
        range = new QueriedRange(range.getStartRow(), range.getEndRow(), range.getStartCol(), range.getEndCol());
        for (GroupByAndFilter gnf : result) {
            if (gnf instanceof Filter) {
                Filter filter = (Filter) gnf;
                FilterAction filterAction = new FilterAction();
                filterAction.filter(grid, range, filter.getConList());
            } else if (gnf instanceof GroupBy) {
                GroupBy groupBy = (GroupBy) gnf;
                GroupByAction groupByAction = new GroupByAction();
                groupByAction.groupBy(grid, range, groupBy.getColList());
            } else if (gnf instanceof Sorting)
            {
                Sorting sort=(Sorting)gnf;
                SortAction sortAction=new SortAction();
                sortAction.sort(grid, range, sort.getCol());
            }
        }
    }
        public void reSet(QueriedRange range)
        {
            List<GroupByAndFilter> result = range.getResult();
        range = new QueriedRange(range.getStartRow(), range.getEndRow(), range.getStartCol(), range.getEndCol());
        for (GroupByAndFilter gnf : result) {
            if (gnf instanceof Filter) {
                Filter filter = (Filter) gnf;
                filter.setQueriedResult(null);
            } else if (gnf instanceof GroupBy) {
                GroupBy groupBy = (GroupBy) gnf;
                groupBy.setQueriedResult(null);
            } else if (gnf instanceof Sorting)
            {
                Sorting sort=(Sorting)gnf;
                sort.setQueriedResult(null);
            }
        }
        }
    
}
