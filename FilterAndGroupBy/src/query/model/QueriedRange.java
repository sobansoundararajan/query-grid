/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import query.exception.QueriedException;
import query.model.GroupBy;
import java.util.*;

/**
 *
 * @author admin
 */
public class QueriedRange {

    private final int startRow;
    private final int endRow;
    private final int startCol;
    private final int endCol;
    private QueriedResult queriedResult;

    private final List<GroupBy> groupByConditionList;
    private Collection<FilterCondition> filterCondition;
    private SortingCondition sortingCondition;
    private Map<Integer, List<FilterOnFunctionCondition>> FilterOnFunctionConditionList;
    private Map<Integer, List<FunctionSortCondition>> functionSortCondition;

    public QueriedRange(int startRow, int endRow, int startCol, int endCol) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
        this.groupByConditionList = new LinkedList();
        this.filterCondition = new LinkedList();
        this.queriedResult = null;
        this.sortingCondition = null;
        this.FilterOnFunctionConditionList = new HashMap();
        this.functionSortCondition = new HashMap();
    }

    public SortingCondition getSortingCondition() {
        return sortingCondition;
    }

    public void setSortingCondition(SortingCondition sortingCondition) {
        this.sortingCondition = sortingCondition;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndCol() {
        return endCol;
    }

    public void addResult(GroupBy obj) {
        groupByConditionList.add(obj);
    }

    public List<GroupBy> getGroupByConditionList() {
        return groupByConditionList;
    }

    public QueriedResult getQueriedResult() throws QueriedException {

        if (this.queriedResult == null && this.groupByConditionList.isEmpty() && this.filterCondition.isEmpty() && this.sortingCondition == null) {
            List<Integer> temp = new LinkedList();
            for (int i = 0; i <= this.endRow - this.startRow; i++) {
                temp.add(i);
            }
            queriedResult = new QueriedResult(temp, null);

        } else {
            if (queriedResult == null) {
                throw new QueriedException("This Result is Reseted to null");
            }
        }
        return queriedResult;
    }

    public Collection<FilterCondition> getFilterConList() {
        return filterCondition;
    }

    public void setFilterCondition(Collection<FilterCondition> filterCondition) {
        this.filterCondition = filterCondition;
    }

    public void setFilterOnFunctionConditionMap(Map<Integer, List<FilterOnFunctionCondition>> FilterOnFunctionConditionList) {
        this.FilterOnFunctionConditionList = FilterOnFunctionConditionList;
    }

    public void setFunctionSortCondition(Map<Integer, List<FunctionSortCondition>> functionSortCondition) {
        this.functionSortCondition = functionSortCondition;
    }

    public void setQueriedResult(QueriedResult queriedResult) {
        this.queriedResult = queriedResult;
    }

    public int getMaxLevel() {
        QueriedResult queriedResult = this.queriedResult;
        int count = 0;
        while (!queriedResult.getNextAction().isEmpty()) {
            count++;
            queriedResult = queriedResult.getNextAction().get(0);
        }
        return count;
    }

    public Map<Integer, List<FilterOnFunctionCondition>> getFilterOnFunctionConditionMap() {
        return FilterOnFunctionConditionList;
    }

    public Map<Integer, List<FunctionSortCondition>> getFunctionSortCondition() {
        return functionSortCondition;
    }
}
