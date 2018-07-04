/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import query.model.GroupByAndFilter;
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

    private final List<GroupByAndFilter> result;

    public QueriedRange(int startRow, int endRow, int startCol, int endCol) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
        this.result = new ArrayList();
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

    public void addResult(GroupByAndFilter obj) {
        result.add(obj);
    }

    public List<GroupByAndFilter> getResult() {
        return result;
    }

    public GroupByAndFilter getLastOperation() {
        return this.result.get(this.result.size() - 1);
    }

    public Set<Set<Integer>> getVisibleRows() {
        VisibleRows visibleRows;
        if (this.result.isEmpty()) {
            Set<Set<Integer>> vr = new HashSet();
            Set<Integer> temp = new LinkedHashSet();
            for (int i = 0; i <= this.endRow - this.startRow; i++) {
                temp.add(i);
            }
            vr.add(temp);
            visibleRows = new VisibleRows(vr);
        } else {
            visibleRows = this.result.get(result.size() - 1).getVisibleRows();
        }
        return visibleRows.getVisibleRows();
    }
}
