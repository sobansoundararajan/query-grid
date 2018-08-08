/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import query.exception.QueriedException;
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

    private final List<GroupBy> groupByList;
    private List<FilterOnRecords> filterOnRecordsList;
    private SortOnRecords sortOnRecords;
    private List<ColumnFormula> columnFormulaList;
    private Map<Integer, List<FilterOnGroups>> filterOnGroupsMap;
    private Map<Integer, List<SortOnGroups>> sortOnGroupsMap;

    public QueriedRange(int startRow, int endRow, int startCol, int endCol) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
        this.groupByList = new LinkedList();
        this.filterOnRecordsList = new LinkedList();
        this.queriedResult = null;
        this.sortOnRecords = null;
        this.columnFormulaList = new LinkedList();
        this.filterOnGroupsMap = new HashMap();
        this.sortOnGroupsMap = new HashMap();
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

    public void addGroupByList(GroupBy groupBy) {
        groupByList.add(groupBy);
    }

    public List<GroupBy> getGroupByList() {
        return groupByList;
    }

    public QueriedResult getQueriedResult() throws QueriedException {

        if (this.queriedResult == null && this.groupByList.isEmpty() && this.filterOnRecordsList.isEmpty() && this.sortOnRecords == null && this.columnFormulaList.isEmpty()) {
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

    public Collection<FilterOnRecords> getFilterOnRecordsList() {
        return filterOnRecordsList;
    }

    public void setFilterOnRecordsList(List<FilterOnRecords> filterOnRecordsList) {
        this.filterOnRecordsList = filterOnRecordsList;
    }

    public List<ColumnFormula> getColumnFormulaList() {
        return columnFormulaList;
    }

    public void setColumnFormulaList(List<ColumnFormula> columnFormulaList) {
        this.columnFormulaList = columnFormulaList;
    }

    public SortOnRecords getSortOnRecords() {
        return sortOnRecords;
    }

    public void setSortOnRecords(SortOnRecords sortOnRecords) {
        this.sortOnRecords = sortOnRecords;
    }

    public void setFilterOnGroupsMap(Map<Integer, List<FilterOnGroups>> filterOnGroupsMap) {
        this.filterOnGroupsMap = filterOnGroupsMap;
    }

    public void setSortOnGroupsMap(Map<Integer, List<SortOnGroups>> sortOnGroupsMap) {
        this.sortOnGroupsMap = sortOnGroupsMap;
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

    public Map<Integer, List<FilterOnGroups>> getFilterOnGroupsMap() {
        return filterOnGroupsMap;
    }

    public Map<Integer, List<SortOnGroups>> getSortOnGroupsMap() {
        return sortOnGroupsMap;
    }
}
