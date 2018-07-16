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

    private final List<GroupBy> groupByResult;
    private Collection<FilterCondition> filterCondition;
    private Collection<SortingCondition> sortingCondition;
    private List<FilterOnFunctionCondition> FilterOnFunctionConditionList;
    private List<FunctionSortCondition> functionSortCondition;

    public QueriedRange(int startRow, int endRow, int startCol, int endCol) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
        this.groupByResult = new ArrayList();
        this.filterCondition= new LinkedList ();
        this.sortingCondition = new LinkedList();
        this.queriedResult=null;
        this.FilterOnFunctionConditionList=new LinkedList ();
        this.functionSortCondition= new LinkedList ();
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
        groupByResult.add(obj);
    }

    public List<GroupBy> getResult() {
        return groupByResult;
    }


    public QueriedResult getQueriedResult() throws QueriedException {
        
        if (this.queriedResult==null&&this.groupByResult.isEmpty()&&this.filterCondition.isEmpty()&&this.sortingCondition.isEmpty()) {
            List<Integer> temp = new LinkedList();
            for (int i = 0; i <= this.endRow - this.startRow; i++) {
                temp.add(i);
            }
            queriedResult=new QueriedResult(temp,null);
            
        } else {
           if(queriedResult==null)
               throw new QueriedException("This Result is Reseted to null");
        }
        return queriedResult;
    }
    
        public Collection<FilterCondition> getFilterConList() {
        return filterCondition;
    }

    public Collection<SortingCondition> getSortingCondition() {
        return sortingCondition;
    }

    public void setQueriedResult(QueriedResult queriedResult) {
        this.queriedResult = queriedResult;
    }
    
    public int getMaxLevel()
    {
        QueriedResult queriedResult=this.queriedResult;
        int count=0;
        while(!queriedResult.getNextAction().isEmpty())
        {
            count++;
            queriedResult=queriedResult.getNextAction().get(0);
        }
        return count;
    }

    public List<FilterOnFunctionCondition> getFilterOnFunctionConditionList() {
        return FilterOnFunctionConditionList;
    }

    public List<FunctionSortCondition> getFunctionSortCondition() {
        return functionSortCondition;
    }
}
