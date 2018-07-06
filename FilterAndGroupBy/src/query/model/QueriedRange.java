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

    public QueriedResult getQueriedResult() throws Exception {
        QueriedResult queriedResult;
        if (this.result.isEmpty()) {
            List<Integer> temp = new LinkedList();
            for (int i = 0; i <= this.endRow - this.startRow; i++) {
                temp.add(i);
            }
            queriedResult=new QueriedResult(temp,null);
            
        } else {
           queriedResult=result.get(result.size()-1).getQueriedResult();
           if(queriedResult==null)
               throw new Exception("This Result is Reseted to null");
        }
        return queriedResult;
    }
}
